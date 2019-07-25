package com.github.shixinke.spring.boot.template.common;


import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * 默认值常量定义
 * @author shixinke
 */
public class Defaults {
    /**
     * JSON格式输出
     */
    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    /**
     * 追踪日志是否开启
     */
    public static final boolean TRACE_LOG_ENABLED = true;
    /**
     * 追踪日志存储类型
     */
    public static final String TRACE_LOG_STORAGE_TYPE = "file";
    /**
     * 追踪日志不存储的URL
     */
    public static final String TRACE_LOG_EXCLUDE_URL = "/";
    /**
     * 不存储的URL前缀
     */
    public static final String TRACE_LOG_EXCLUDE_URL_PREFIX = "";
    /**
     * 存储的URL列表
     */
    public static final String TRACE_LOG_INCLUDE_URL = "";
    /**
     * 存储的URL前缀
     */
    public static final String TRACE_LOG_INCLUDE_URL_PREFIX = "";
    /**
     * 追踪日志保留的天数
     */
    public static final int TRACE_LOG_REMAIN_DAYS = 7;

    /**
     * 线程数与处理器的倍数
     */
    public static final int MULTIPLE_NUMBER_OF_PROCESSORS = 4;

    /**
     * 线程池队列大小
     */
    public static final int THREAD_POOL_QUEUE_CAPACITY = 500;

    /**
     * 保持存活的时间
     */
    public static final int THREAD_POOL_KEEP_ALIVE_SECONDS = 60;

    /**
     * 是否允许核心线程池超时
     */
    public static final boolean THREAD_POOL_ALLOW_CORE_THREAD_TIMEOUT = false;


    /**
     * 表单对应的content-type
     */
    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";

    /**
     * 默认接受的语言
     */
    public static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;";

    /**
     * HTTP连接池最大数
     */
    public static final int HTTP_POOL_MAX_SIZE = 200;

    /**
     * HTTP连接超时时间
     */
    public static final int HTTP_CONNECT_TIMEOUT_MS = 6000;
    /**
     * HTTP读操作超时时间
     */
    public static final int HTTP_READ_TIMEOUT_MS = 6000;

    /**
     * 默认的用户userAgent
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";
    /**
     * jsoup默认超时时间
     */
    public static final int JSOUP_TIMEOUT_MS = 30000;


    /**
     * jsoup默认重试的次数
     */
    public static final int JSOUP_RETRY_TIMES = 3;

    /**
     * HTTP重试次数
     */
    public static final int HTTP_CLIENT_RETRY_TIMES = 2;

    /**
     * Redis服务器地址
     */
    public static final String REDIS_HOST = "127.0.0.1";
    /**
     * Redis服务器端口
     */
    public static final int REDIS_PORT = 6379;

    /**
     * 连接超时时间
     */
    public static final int REDIS_CONNECT_TIMEOUT = 3000;
    /**
     * Redis池最大活动连接数
     */
    public static final int REDIS_POOL_MAX_ACTIVE = 10000;
    /**
     * Redis最大空闲时间
     */
    public static final int REDIS_POOL_MAX_IDLE = 1000;
    /**
     * 最大等待时间
     */
    public static final int REDIS_POOL_MAX_WAIT = 1500;
    /**
     * 最小空闲时间
     */
    public static final int REDIS_POOL_MIN_IDLE = 5;



}
