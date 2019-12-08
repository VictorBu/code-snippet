package com.karonda.server;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChannelSupervise {

    public static final String COUNTER_ATTR = "COUNTER_ATTR";

    private static ConcurrentMap<String, Channel> ChannelMap = new ConcurrentHashMap();

    public static void addChannel(Channel channel){
        ChannelMap.put(channel.id().asShortText(), channel);
    }
    public static void removeChannel(Channel channel){
        ChannelMap.remove(channel.id().asShortText());
    }
}
