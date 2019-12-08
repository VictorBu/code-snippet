package com.karonda.server;

import com.karonda.common.netty.NettyMessage;
import com.karonda.common.netty.NettyMessageTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatHandler.class);

    private final AttributeKey<Integer> counterAttr = AttributeKey.valueOf(ChannelSupervise.COUNTER_ATTR);;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

        if(evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    NettyMessage<String> nettyMessage = new NettyMessage<>();
                    nettyMessage.setSessionId(0L);
                    nettyMessage.setType(NettyMessageTypeEnum.HEARTBEAT);

                    ctx.writeAndFlush(nettyMessage).addListener(future -> {
//                        if(future.isSuccess()) {
//                            ctx.channel().attr(counterAttr).set(0);
//                        }else {
                            Integer counter = ctx.channel().attr(counterAttr).get();
                            counter = counter + 1;
                            LOGGER.info(ctx.channel().id().asShortText() + "，发送心跳: " + counter);
                            if(counter >= 3) {
                                ctx.close();
                            } else {
                                ctx.channel().attr(counterAttr).set(counter);
                            }
//                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(counterAttr).set(0);
        ChannelSupervise.addChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelSupervise.removeChannel(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ctx.channel().attr(counterAttr).set(0);
        ctx.fireChannelRead(msg);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("断开连接", cause);
        ctx.close();
    }
}
