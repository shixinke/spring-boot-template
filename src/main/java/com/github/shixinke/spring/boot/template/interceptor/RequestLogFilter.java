package com.github.shixinke.spring.boot.template.interceptor;


import com.github.shixinke.spring.boot.template.common.Constants;
import com.github.shixinke.spring.boot.template.component.TraceLogComponent;
import com.shixinke.utils.web.util.TraceLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求日志过滤器
 * @author shixinke
 */
@Slf4j
public class RequestLogFilter implements Filter {

    @Resource
    private TraceLogComponent traceLogComponent;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put(Constants.TRACE_LOG_ID, TraceLogUtil.getTraceId());
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        traceLogComponent.log(request, (HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
