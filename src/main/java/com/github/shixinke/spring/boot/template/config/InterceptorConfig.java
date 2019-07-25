package com.github.shixinke.spring.boot.template.config;

import com.shixinke.utils.web.interceptor.RequestParameterResolver;
import com.github.shixinke.spring.boot.template.interceptor.NeedLoginInterceptor;
import com.github.shixinke.spring.boot.template.interceptor.RequestLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 拦截器注解
 * @author shixinke
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(needLoginInterceptor());

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requestParameterResolver());
    }


    @Bean
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestLogFilter());
        registration.setName("requestLogFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public RequestLogFilter requestLogFilter() {
        return new RequestLogFilter();
    }
    @Bean
    public NeedLoginInterceptor needLoginInterceptor() {
        return new NeedLoginInterceptor();
    }

    public RequestParameterResolver requestParameterResolver() {
        return new RequestParameterResolver();
    }
}
