package com.karonda.client;

import com.karonda.common.netty.NettyMessage;
import com.karonda.common.netty.NettyMessageTypeEnum;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            switch (idleStateEvent.state()){
                case WRITER_IDLE:
                    LOGGER.info("发送心跳包");
                    NettyMessage<String> nettyMessage = new NettyMessage<>();
                    nettyMessage.setSessionId(0L);
                    nettyMessage.setType(NettyMessageTypeEnum.HEARTBEAT);
                    ctx.writeAndFlush(nettyMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    break;
                default:
                    break;
            }
        }

        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof NettyMessage) {
            switch (((NettyMessage)msg).getType()) {
                case HEARTBEAT:
                    LOGGER.info("接收到服务端心跳包: {}", ctx.channel().id().asShortText());
                    break;
                default:
                    break;
            }
        }
    }
}
