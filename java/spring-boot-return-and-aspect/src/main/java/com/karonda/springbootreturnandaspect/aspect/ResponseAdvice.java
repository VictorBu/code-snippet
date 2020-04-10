package com.karonda.springbootreturnandaspect.aspect;

import com.karonda.springbootreturnandaspect.core.CommonReturn;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // swagger 不处理
        String uri = request.getURI().toString();
        if(uri.contains("/v2/api-docs") || uri.contains("/swagger-resources") || uri.contains("/swagger-ui.html")) {
            return body;
        }

        if(body == null) {
            return CommonReturn.success(null);
        } else if(body instanceof CommonReturn) {
            return body;
        }

        return CommonReturn.success(body);
    }
}
