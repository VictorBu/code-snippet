package com.karonda.server;

import com.karonda.common.netty.NettyMessageDecoder;
import com.karonda.common.netty.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerRunner.class);


    @Value("${server.socket-port}")
    private int socketPort;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    @Override
    public void run(String... args) throws Exception {
        this.bind();
    }

    private void bind() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast("ping", new IdleStateHandler(10, 5, 10));
                        channel.pipeline().addLast("encoder", new NettyMessageEncoder());
                        channel.pipeline().addLast("decoder", new NettyMessageDecoder());
                        channel.pipeline().addLast("message", new MessageHandler());
                        channel.pipeline().addLast("heartbeat", new HeartbeatHandler());
                    }
                });

        ChannelFuture future = serverBootstrap.bind(socketPort).sync();

        future.addListener(fl -> {
            if (fl.isSuccess()) {
                serverChannel = future.channel();
                LOGGER.info("服务端启动，端口：" + socketPort);
            }
        });

        future.channel().closeFuture().addListener(fl -> {
            this.close();
            LOGGER.info("服务端关闭");
        });
    }

    private void close() {
        if (serverChannel != null) {
            serverChannel.close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
