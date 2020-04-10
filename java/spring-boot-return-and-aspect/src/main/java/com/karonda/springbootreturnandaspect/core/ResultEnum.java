package com.karonda.springbootreturnandaspect.core;

public enum ResultEnum implements CommonResult {

    SUCCESS(0, "成功。"),

    PARAM_ERROR(1001,"参数验证失败。"),

    UNKNOWN_ERROR(9999,"未知错误。"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
