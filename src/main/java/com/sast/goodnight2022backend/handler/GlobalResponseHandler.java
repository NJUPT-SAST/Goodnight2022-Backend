package com.sast.goodnight2022backend.handler;

import com.google.gson.Gson;
import com.sast.goodnight2022backend.enums.ErrorEnum;
import com.sast.goodnight2022backend.response.GlobalResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author NuoTian
 * @date 2022/12/24
 */
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NotNull MethodParameter returnType,
                            @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NotNull MethodParameter returnType,
                                  @NotNull MediaType selectedContentType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NotNull ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {
        if (body == null) {
            return GlobalResponse.success();
        } else if (body instanceof GlobalResponse) {
            return body;
        } else if (body instanceof String) {
            return new Gson().toJson(GlobalResponse.success((String) body));
        } else if (body instanceof ErrorEnum) {
            return GlobalResponse.failure((ErrorEnum) body);
        }
        return GlobalResponse.success(body);
    }
}
