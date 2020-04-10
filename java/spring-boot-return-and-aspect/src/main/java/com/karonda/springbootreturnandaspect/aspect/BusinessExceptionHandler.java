package com.karonda.springbootreturnandaspect.aspect;

import com.karonda.springbootreturnandaspect.core.BusinessException;
import com.karonda.springbootreturnandaspect.core.CommonReturn;
import com.karonda.springbootreturnandaspect.core.ResultEnum;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {
    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleBindException(MethodArgumentNotValidException ex) {

        StringBuilder errMsgBuilder = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errMsgBuilder.append(fieldError.getDefaultMessage());
        });

        return CommonReturn.error(ResultEnum.PARAM_ERROR, errMsgBuilder.toString());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException ex){
        return CommonReturn.error(ex);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex){
        return CommonReturn.error(ResultEnum.UNKNOWN_ERROR, ex.getMessage());
    }
}
