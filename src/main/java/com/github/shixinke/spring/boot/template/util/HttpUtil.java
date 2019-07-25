package com.github.shixinke.spring.boot.template.util;

import com.alibaba.fastjson.JSONObject;
import com.github.shixinke.spring.boot.template.common.Defaults;
import org.apache.commons.lang3.StringUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * HTTP网络工具类
 * @author shixinke
 */
public class HttpUtil {

    public static Map<String, String> getJsoupHeaders() {
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Accept-Language", Defaults.ACCEPT_LANGUAGE);
        headers.put("user-agent",  Defaults.USER_AGENT);
        return headers;
    }

    /**
     * 解析URL
     * @param url
     * @return
     */
    public static JSONObject parseUrl(String url) {
        JSONObject urlMap = new JSONObject();
        int pos = url.indexOf("?");
        if (pos >= 0) {
            urlMap.put("url", url.substring(0, pos));
            String queryString = url.substring(pos + 1);
            urlMap.put("params", parseQueryString(queryString));
        } else {
            urlMap.put("url", url);
        }
        return urlMap;
    }

    /**
     * 解析查询字符串
     * @param queryString
     * @return
     */
    public static JSONObject parseQueryString(String queryString) {
        JSONObject params = new JSONObject();
        if (StringUtils.isNotBlank(queryString)) {
            String queryStringPrefix = "?";
            if (queryString.startsWith(queryStringPrefix)) {
                queryString = queryString.substring(1);
            }
            String[] arr = queryString.split("&");
            for (int i = 0; i < arr.length; i++) {
                String[] tmp = arr[i].split("=");
                if (tmp.length > 0) {
                    if (tmp.length > 1) {
                        params.put(tmp[0], tmp[1]);
                    } else {
                        params.put(tmp[0], "");
                    }
                }
            }
        }
        return params;
    }
}
