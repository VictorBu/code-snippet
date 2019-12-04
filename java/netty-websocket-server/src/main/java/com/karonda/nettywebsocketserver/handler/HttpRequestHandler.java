package com.karonda.nettywebsocketserver.handler;

import com.karonda.nettywebsocketserver.netty.ChannelSupervise;
import com.karonda.nettywebsocketserver.util.UriUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHandler.class);

    @Value("${server.socket-uri}")
    private String socketUri;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (msg.uri().startsWith(socketUri)) {
            String userId = UriUtil.getParam(msg.uri(), "userId");
            if (userId != null) {
                // todo: 用户校验，重复登录判断
                ChannelSupervise.addChannel(userId, ctx.channel());
                ctx.fireChannelRead(msg.setUri(socketUri).retain());
            } else {
                ctx.close();
            }
        } else {
            ctx.close();
        }
    }

}
