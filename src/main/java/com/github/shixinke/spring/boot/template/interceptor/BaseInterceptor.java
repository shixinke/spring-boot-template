package com.github.shixinke.spring.boot.template.interceptor;

import com.alibaba.fastjson.JSON;
import com.github.shixinke.spring.boot.template.common.Defaults;
import com.shixinke.utils.web.common.Errors;
import com.shixinke.utils.web.common.ResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基础拦截器
 * @author shixinke
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

    /**
     * 拦截方法
     * @param response
     * @param code
     * @param message
     * @return
     * @throws IOException
     */
    protected boolean intercept(HttpServletResponse response, int code, String message) throws IOException {
        ResponseDTO responseDTO = ResponseDTO.error(code, message);
        response.setHeader(HttpHeaders.CONTENT_TYPE, Defaults.APPLICATION_JSON_UTF8);
        response.getWriter().write(JSON.toJSONString(responseDTO));
        return false;
    }

    /**
     * 拦截方法
     * @param response
     * @param error
     * @return
     * @throws IOException
     */
    protected boolean intercept(HttpServletResponse response, Errors error) throws IOException {
        return intercept(response, error.getCode(), error.getMessage());
    }
}
