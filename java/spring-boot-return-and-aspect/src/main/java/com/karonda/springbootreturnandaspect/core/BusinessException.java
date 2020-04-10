package com.karonda.springbootreturnandaspect.core;

public class BusinessException extends Exception implements CommonResult {

    private CommonResult commonResult;

    public BusinessException(CommonResult commonResult) {
        super();
        this.commonResult = commonResult;
    }

    public BusinessException(CommonResult commonResult, String msg) {
        super();
        this.commonResult = commonResult;
        this.setMsg(msg);
    }


    @Override
    public Integer getCode() {
        return this.commonResult.getCode();
    }

    @Override
    public String getMsg() {
        return this.commonResult.getMsg();
    }

    @Override
    public void setMsg(String msg) {
        this.commonResult.setMsg(msg);
    }
}
