package com.github.shixinke.spring.boot.template.config;



import com.github.shixinke.spring.boot.template.common.AppConfigKeys;
import com.github.shixinke.spring.boot.template.common.Defaults;
import com.github.shixinke.spring.boot.template.component.AppConfigComponent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * restTemplate配置(HTTP客户端)
 * @author shixinke
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    @Resource
    private AppConfigComponent appConfigComponent;

    @Bean
    @Scope("prototype")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        /**
         * 使用 utf-8 编码集的 converter 替换默认的 converter（默认的 string converter 的编码集为"ISO-8859-1"）
         */

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                //iterator.remove();
            }
        }
        //messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        //restTemplate.setMessageConverters(messageConverters);
        /**
         * RestTemplate默认对非200状态直接抛异常，现在让非200也可以返回
         */
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return true;
            }
            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                log.error("StatusCode=" + clientHttpResponse.getStatusCode());
            }
        };
        return restTemplate;
    }


    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();

        clientHttpRequestFactory.setConnectTimeout(appConfigComponent.getProperty(AppConfigKeys.HTTP_CLIENT_CONNECT_TIMEOUT_MS, Integer.class, Defaults.HTTP_CONNECT_TIMEOUT_MS));
        clientHttpRequestFactory.setReadTimeout(appConfigComponent.getProperty(AppConfigKeys.HTTP_CLIENT_READ_TIMEOUT_MS, Integer.class, Defaults.HTTP_READ_TIMEOUT_MS));
        Boolean proxyFlag = appConfigComponent.getProperty(AppConfigKeys.HTTP_PROXY_ENABLED, Boolean.class);
        if(proxyFlag != null && proxyFlag) {
            SocketAddress address = new InetSocketAddress(appConfigComponent.getProperty(AppConfigKeys.HTTP_PROXY_HOST), appConfigComponent.getProperty(AppConfigKeys.HTTP_PROXY_PORT, Integer.class));
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            clientHttpRequestFactory.setProxy(proxy);
        }
        return clientHttpRequestFactory;
    }
}
