package com.karonda.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class NettyMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        NettyMessage<String> nettyMessage = new NettyMessage<>();
        nettyMessage.setSessionId(in.readLong());
        nettyMessage.setType(NettyMessageTypeEnum.fromType(in.readByte()));
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        nettyMessage.setBody(new String(bytes));

        out.add(nettyMessage);
    }
}
