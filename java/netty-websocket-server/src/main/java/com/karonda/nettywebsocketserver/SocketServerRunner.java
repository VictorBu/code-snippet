package com.karonda.nettywebsocketserver;

import com.karonda.nettywebsocketserver.handler.HttpRequestHandler;
import com.karonda.nettywebsocketserver.handler.TextWebSocketFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketServerRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServerRunner.class);


    @Value("${server.socket-port}")
    private int socketPort;
    @Value("${server.socket-uri}")
    private String socketUri;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    @Autowired
    private HttpRequestHandler httpRequestHandler;
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;

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
                // 日志监听
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel> () {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        // WebSocket 是基于 Http 协议的，要使用 Http 解编码器
                        channel.pipeline().addLast("http-codec", new HttpServerCodec());
                        // 用于大数据流的分区传输
                        channel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                        // 将多个消息转换为单一的 request 或者 response 对象，最终得到的是 FullHttpRequest 对象
                        channel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
                        // 创建 WebSocket 之前会有唯一一次 Http 请求 (Header 中包含 Upgrade 并且值为 websocket)
                        channel.pipeline().addLast("http-request",httpRequestHandler);
                        // 处理所有委托管理的 WebSocket 帧类型以及握手本身
                        // 入参是 ws://server:port/context_path 中的 contex_path
                        channel.pipeline().addLast("websocket-server", new WebSocketServerProtocolHandler(socketUri));
                        // WebSocket RFC 定义了 6 种帧，TextWebSocketFrame 是我们唯一真正需要处理的帧类型
                        channel.pipeline().addLast("text-frame",textWebSocketFrameHandler);
                    }
                });

        ChannelFuture future = serverBootstrap.bind(socketPort).sync();

        future.addListener(fl -> {
            if (fl.isSuccess()) {
                serverChannel = future.channel();
                LOGGER.info("WebSocket 服务端启动，端口：" + socketPort);
            }
        });

        future.channel().closeFuture().addListener(fl -> {
            this.close();
            LOGGER.info("WebSocket 服务端关闭");
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
