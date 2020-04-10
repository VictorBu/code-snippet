package com.karonda.springbootreturnandaspect.core;

public class CommonReturn {
    private Integer code;
    private String msg;
    private Object data;

    private CommonReturn(CommonResult commonResult) {
        this.code = commonResult.getCode();
        this.msg = commonResult.getMsg();
    }

    public static CommonReturn success(Object obj) {
        CommonReturn commonReturn = new CommonReturn(ResultEnum.SUCCESS);
        commonReturn.data = obj;
        return commonReturn;
    }

    public static CommonReturn error(CommonResult commonResult) {
        CommonReturn commonReturn = new CommonReturn(commonResult);
        return commonReturn;
    }

    public static CommonReturn error(CommonResult commonResult, String msg) {
        CommonReturn commonReturn = new CommonReturn(commonResult);
        commonReturn.msg = msg;
        return commonReturn;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
