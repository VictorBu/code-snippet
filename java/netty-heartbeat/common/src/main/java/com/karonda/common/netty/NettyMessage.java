package com.karonda.common.netty;

public class NettyMessage<T> {

    private long sessionId;
    private NettyMessageTypeEnum type;
    private T body;

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public NettyMessageTypeEnum getType() {
        return type;
    }

    public void setType(NettyMessageTypeEnum type) {
        this.type = type;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
