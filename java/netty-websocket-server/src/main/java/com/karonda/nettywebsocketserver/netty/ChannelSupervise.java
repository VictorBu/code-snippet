package com.karonda.nettywebsocketserver.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChannelSupervise {

    private static ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ConcurrentMap<String, ChannelId> UserChannelMap = new ConcurrentHashMap();
    private static ConcurrentMap<String, String> ChannelUserMap = new ConcurrentHashMap();

    public static void addChannel(String userId, Channel channel){
        GlobalGroup.add(channel);
        UserChannelMap.put(userId, channel.id());
        ChannelUserMap.put(channel.id().asShortText(), userId);
    }
    public static void removeChannel(Channel channel){
        GlobalGroup.remove(channel);
        String userId = ChannelUserMap.get(channel.id().asShortText());
        UserChannelMap.remove(userId);
        ChannelUserMap.remove(channel.id().asShortText());
    }
    public static void sendToUser(String userId, String msg){
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg);
        Channel channel = GlobalGroup.find(UserChannelMap.get(userId));
        channel.writeAndFlush(textWebSocketFrame);
    }
    public static void sendToAll(String msg){
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg);
        GlobalGroup.writeAndFlush(textWebSocketFrame);
    }
}
