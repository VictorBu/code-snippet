package com.karonda.common.netty;

public enum NettyMessageTypeEnum {

    LOGIN((byte)0x00),
    COMMAND((byte)0x01),
    HEARTBEAT((byte)0x7F),
    ;
    private final byte type;

    private NettyMessageTypeEnum(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public static NettyMessageTypeEnum fromType(byte type) throws Exception {
        for (NettyMessageTypeEnum nettyMessageTypeEnum : values()) {
            if(type == nettyMessageTypeEnum.type) {
                return nettyMessageTypeEnum;
            }
        }

        throw new Exception("未识别的命令");
    }
}
