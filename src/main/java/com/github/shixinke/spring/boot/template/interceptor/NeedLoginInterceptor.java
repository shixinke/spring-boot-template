package com.github.shixinke.spring.boot.template.interceptor;

import com.github.shixinke.spring.boot.template.annotation.NeedLogin;
import com.github.shixinke.spring.boot.template.common.Constants;
import com.shixinke.utils.web.common.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未登录注解解析器
 * @author shixinke
 */
@Slf4j
public class NeedLoginInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NeedLogin needLogin = handlerMethod.getMethodAnnotation(NeedLogin.class);
        if (needLogin == null) {
            needLogin = handlerMethod.getBeanType().getAnnotation(NeedLogin.class);
        }
        if (needLogin != null && needLogin.value()) {
            return true;
        }
        String accessToken = request.getHeader(Constants.ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            return intercept(response, Errors.NOT_LOGIN);
        }

        return true;
    }


}
