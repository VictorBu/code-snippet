package com.karonda.client;

import com.karonda.common.netty.NettyMessageDecoder;
import com.karonda.common.netty.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClientRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRunner.class);

    @Value("${server.socket-ip}")
    private String socketIp;
    @Value("${server.socket-port}")
    private int socketPort;

    private EventLoopGroup workerGroup;
    private Channel clientChannel;

    @Override
    public void run(String... args) throws Exception {
        this.bind();
    }

    private void bind() throws InterruptedException {
        workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast("ping", new IdleStateHandler(5, 5, 3));
                        channel.pipeline().addLast("encoder", new NettyMessageEncoder());
                        channel.pipeline().addLast("decoder", new NettyMessageDecoder());
                        channel.pipeline().addLast("heartbeat", new HeartbeatHandler());
                        channel.pipeline().addLast("logger", new LoggingHandler(LogLevel.INFO));
                    }
                });

        ChannelFuture future = bootstrap.connect(socketIp, socketPort).sync();

        future.addListener(fl -> {
            if (fl.isSuccess()) {
                clientChannel = future.channel();
                LOGGER.info("服务端连接成功");
            }
        });

        future.channel().closeFuture().addListener(fl -> {
            this.close();
            LOGGER.info("服务端连接关闭");
        });
    }

    private void close() {
        if (clientChannel != null) {
            clientChannel.close();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
