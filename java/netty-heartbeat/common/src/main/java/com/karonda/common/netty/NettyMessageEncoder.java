package com.karonda.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        out.writeLong(msg.getSessionId());
        out.writeByte(msg.getType().getType());
        if(msg.getBody() != null) {
            out.writeBytes(msg.getBody().toString().getBytes());
        }
    }
}
