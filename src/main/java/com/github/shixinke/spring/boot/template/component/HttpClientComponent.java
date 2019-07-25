package com.github.shixinke.spring.boot.template.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.rholder.retry.*;
import com.google.common.base.Predicate;
import com.shixinke.utils.web.common.Errors;
import com.shixinke.utils.web.common.ResponseDTO;
import com.github.shixinke.spring.boot.template.common.AppConfigKeys;
import com.github.shixinke.spring.boot.template.common.Defaults;

import com.github.shixinke.spring.boot.template.util.GzipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * http客户端请求组件
 * @author shixinke
 */
@Component
@Slf4j
public class HttpClientComponent {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JsoupComponent jsoupComponent;

    @Resource
    private AppConfigComponent appConfigComponent;


    /**
     * 发起GET请求
     * @param url
     * @param headers
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> ResponseDTO<T> get(String url, Map<String, String> headers, Class<T> clazz, boolean retry, int count) {
        return request(HttpMethod.GET, url, null, headers, clazz);
    }

    /**
     * 发起GET请求(带重试)
     * @param url
     * @param headers
     * @param clazz
     * @param retryTimes
     * @param <T>
     * @return
     */
    public <T> ResponseDTO<T> get(String url, Map<String, String> headers, Class<T> clazz, Integer retryTimes) {
        if (retryTimes == null) {
            retryTimes = appConfigComponent.getProperty(AppConfigKeys.HTTP_CLIENT_RETRY_TIMES, Integer.class, Defaults.HTTP_CLIENT_RETRY_TIMES);
        }
        final Integer times = retryTimes;
        Callable<ResponseDTO<T>> callable = new Callable<ResponseDTO<T>>() {
            int count = 0;
            @Override
            public ResponseDTO<T> call()  {
                boolean retry  = false;
                if (times != null && times > 0) {
                    retry = true;
                }
                return get(url, headers, clazz, retry, count ++);
            }
        };
        return retry(callable, times);
    }

    /**
     * Get请求
     * @param url
     * @param headers
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> ResponseDTO<T> get(String url, Map<String, String> headers, Class<T> clazz) {
        return get(url, headers, clazz, null);
    }


    /**
     * 发起POST请求
     * @param url
     * @param params
     * @param headers
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> ResponseDTO<T> post(String url, Map<String, String> params, Map<String, String> headers, Class<T> clazz, boolean retry, int count) {
        return request(HttpMethod.POST, url, params, headers, clazz);
    }

    /**
     * 发起POST请求(带重试)
     * @param url
     * @param params
     * @param headers
     * @param clazz
     * @param retryTimes
     * @param <T>
     * @return
     */
    public <T> ResponseDTO<T> post(String url, Map<String, String> params, Map<String, String> headers, Class<T> clazz, Integer retryTimes) {
        if (retryTimes == null) {
            retryTimes = appConfigComponent.getProperty(AppConfigKeys.HTTP_CLIENT_RETRY_TIMES, Integer.class, Defaults.HTTP_CLIENT_RETRY_TIMES);
        }
        final Integer times = retryTimes;
        Callable<ResponseDTO<T>> callable = new Callable<ResponseDTO<T>>() {
            int count = 0;
            @Override
            public ResponseDTO<T> call()  {
                boolean retry  = false;
                if (times != null && times > 0) {
                    retry = true;
                }
                log.info("count={};retry={}", count, retry);
                return post(url, params, headers, clazz, retry, count ++);
            }
        };
        return retry(callable, times);
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> ResponseDTO<T> post(String url, Map<String, String> params, Map<String, String> headers, Class<T> clazz) {
        return post(url, params, headers, clazz, null);
    }

    /**
     * 抓取文档
     * @param url
     * @param headers
     * @return
     */
    public ResponseDTO<Document> getDocument(String url, Map<String, String> headers, boolean retry, Integer count) {
        ResponseDTO<Document> responseDTO = ResponseDTO.success();
        try {
            Document document = jsoupComponent.get(url, null, headers);
            if (document == null) {
                responseDTO.setError(Errors.EMPTY_CONTENT);
            } else {
                responseDTO.setData(document);
            }
            log.info("url={}; count={}; document={}", url, count, document);
        } catch (HttpStatusException statusException) {
            log.error("url={}; count={}; statusCode={}", url, count, statusException.getStatusCode(), statusException);
            responseDTO.setError(statusException.getStatusCode(), "获取页面失败");
        } catch (Exception ex) {
            log.error("url={}; count={}", url, count, ex);
            responseDTO.setError(Errors.IO_EXCEPTION.getCode(), "获取页面失败");
        }
        return responseDTO;
    }


    /**
     * 抓取文档(带重试)
     * @param url
     * @param headers
     * @param retryTimes
     * @return
     */
    public ResponseDTO<Document> getDocument(String url,  Map<String, String> headers, Integer retryTimes) {
        if (retryTimes == null) {
            retryTimes = appConfigComponent.getProperty(AppConfigKeys.JSOUP_RETRY_TIMES, Integer.class, Defaults.JSOUP_RETRY_TIMES);
        }
        final Integer times = retryTimes;
        Callable<ResponseDTO<Document>> callable = new Callable<ResponseDTO<Document>>() {
            int count = 0;
            @Override
            public ResponseDTO<Document> call()  {
                boolean retry = false;
                if (times != null && times > 0) {
                    retry = true;
                }
                log.info("url={};headers={};count={};obj={}", url, headers, count, this);
                return getDocument(url, headers, retry, count ++);
            }
        };

        return retry(callable, times);
    }

    public ResponseDTO<Document> getDocument(String url, Map<String, String> headers) {
        return getDocument(url, headers, null);
    }


    /**
     * 重试
     * @param callable
     * @param retryTimes
     * @param <T>
     * @return
     */
    private <T> ResponseDTO<T> retry(Callable<ResponseDTO<T>> callable, Integer retryTimes) {
        List<Integer> scenes = appConfigComponent.getPropertyList(AppConfigKeys.HTTP_CLIENT_RETRY_SCENE, Integer.class);
        Predicate<ResponseDTO<T>> resultPredicate = new Predicate<ResponseDTO<T>>() {
            @Override
            public boolean apply(@Nullable ResponseDTO responseDTO) {
                if (responseDTO == null || responseDTO.getSuccess() == null) {
                    return false;
                }
                Integer errorCode = 0;
                if (responseDTO.getCode() != null) {
                    errorCode = responseDTO.getCode();
                }
                if (scenes.contains(errorCode)) {
                    return true;
                }
                return false;
            }
        };
        if (retryTimes == null) {
            retryTimes = appConfigComponent.getProperty(AppConfigKeys.HTTP_CLIENT_RETRY_TIMES, Integer.class, Defaults.HTTP_CLIENT_RETRY_TIMES);
        }

        Retryer<ResponseDTO<T>> retryer = RetryerBuilder.<ResponseDTO<T>>newBuilder()
                .retryIfResult(resultPredicate)
                .withStopStrategy(StopStrategies.stopAfterAttempt(retryTimes + 1))
                .build();
        ResponseDTO responseDTO = ResponseDTO.error();
        try {
            responseDTO = retryer.call(callable);
        } catch (RetryException e) {
            log.error("重试出错:", e);
            responseDTO = ResponseDTO.error(Errors.RETRY_EXCEPTION);
        } catch (ExecutionException e) {
            log.error("重试执行出错:", e);
            responseDTO = ResponseDTO.error(Errors.EXECUTION_EXCEPTION);
        }
        return responseDTO;
    }


    /**
     * 发起网络请求
     * @param method
     * @param url
     * @param params
     * @param headers
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> ResponseDTO<T> request(HttpMethod method, String url, Map<String, String> params, Map<String, String> headers, Class<T> clazz) {
        ResponseDTO<T> response = new ResponseDTO<>();
        MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                requestEntity.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestEntity, httpHeaders);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, String.class);
        ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);
        T resp = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.execute(url, method, requestCallback, responseExtractor);
            if (responseEntity != null) {
                String body = responseEntity.getBody();
                log.info("headers={}, body={}", responseEntity.getHeaders(), body);
                if (clazz != String.class) {
                    resp = JSONObject.parseObject(body, clazz);
                } else {
                    resp = (T) body;
                }
                response.setData(resp);
            }
            log.info("url = {}; headers = {}; params = {}, response = {}", url, httpHeaders, params, JSON.toJSONString(response));
        } catch (HttpStatusCodeException e) {
            int statusCode = e.getStatusCode().value();
            String errorMsg = e.getResponseBodyAsString();
            if (StringUtils.isNotBlank(errorMsg)) {
                errorMsg = GzipUtil.uncompress(e.getResponseBodyAsByteArray());
            }
            response.setError(statusCode, errorMsg);
            log.error("url:{}; headers = {}; statusCode = {}; response = {};exception=", url, httpHeaders, statusCode, errorMsg,  e);
        } catch (Exception e) {
            log.error("url:{}; headers = {}; ", url, httpHeaders, e);
            response.setError(Errors.NETWORK_ERROR.getCode(), "请求接口出错");
        }
        response.setData(resp);
        return response;
    }
}
