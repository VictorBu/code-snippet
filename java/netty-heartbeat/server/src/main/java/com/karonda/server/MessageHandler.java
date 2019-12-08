package com.karonda.server;

import com.karonda.common.netty.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage<String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage<String> msg) throws Exception {

        switch (msg.getType()) {
            case HEARTBEAT:
                LOGGER.info("接收到客户端心跳包: {}", ctx.channel().id().asShortText());
                break;
            default:
                break;
        }
    }
}
