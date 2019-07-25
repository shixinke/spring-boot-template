package com.github.shixinke.spring.boot.template.component;

import com.github.shixinke.spring.boot.template.common.AppConfigKeys;
import com.github.shixinke.spring.boot.template.common.Defaults;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * Jsoup组件(用于抓取页面数据)
 * @author shixinke
 */
@Component
public class JsoupComponent {

    @Resource
    private AppConfigComponent appConfigComponent;

    /**
     * 获取连接
     * @param url
     * @param cookies
     * @param headers
     * @return
     * @throws IOException
     */
    private Connection getConnection(String url, Map<String, String> cookies, Map<String, String> headers, Map<String, String> body) throws IOException {
        Connection conn = Jsoup.connect(url)
                .cookies(cookies).headers(headers);
        int timeout = appConfigComponent.getProperty(AppConfigKeys.JSOUP_TIMEOUT_MS, Integer.class, Defaults.JSOUP_TIMEOUT_MS);
        if (timeout > 0) {
            conn.timeout(timeout);
        }
        if (body != null) {
            conn.data(body).postDataCharset("UTF-8");
        }
        boolean proxyEnabled = appConfigComponent.getProperty(AppConfigKeys.HTTP_PROXY_ENABLED, Boolean.class, false);
        if(proxyEnabled) {
            String proxyHost = appConfigComponent.getProperty(AppConfigKeys.HTTP_PROXY_HOST);
            Integer proxyPort = appConfigComponent.getProperty(AppConfigKeys.HTTP_PROXY_PORT, Integer.class);
            if (StringUtils.isNotBlank(proxyHost) && proxyPort != null && proxyPort > 0) {
                conn.proxy(proxyHost, proxyPort);
            }
        }
        return conn;

    }

    /**
     * 获取某个页面
     * @param url
     * @param cookies
     * @param headers
     * @return Document
     * @throws IOException
     */
    public Document get(String url, Map<String, String> cookies, Map<String, String> headers) throws IOException {
        Connection conn = getConnection(url, cookies, headers, null);
        return conn.get();
    }

    /**
     * 发起post请求并获取页面文档
     * @param url
     * @param cookies
     * @param headers
     * @param body
     * @return Document
     * @throws IOException
     */
    public Document post(String url, Map<String, String> cookies, Map<String, String> headers, Map<String, String> body) throws IOException  {
        Connection conn = getConnection(url, cookies, headers, body);
        return conn.get();
    }

    public Document parse(String content) {
        return Jsoup.parse(content);
    }
}
