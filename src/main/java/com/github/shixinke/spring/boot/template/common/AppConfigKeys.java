package com.github.shixinke.spring.boot.template.common;

/**
 * 配置文件键定义
 * @author shixinke
 */
public class AppConfigKeys {
    /**
     * Redis服务器地址
     */
    public static final String REDIS_HOST = "redis.host";
    /**
     * Redis服务器端口
     */
    public static final String REDIS_PORT = "redis.port";
    /**
     * Redis密码
     */
    public static final String REDIS_PASSWORD = "redis.password";
    /**
     * 连接超时时间
     */
    public static final String REDIS_CONNECT_TIMEOUT = "redis.connect.timeout";
    /**
     * Redis池最大活动连接数
     */
    public static final String REDIS_POOL_MAX_ACTIVE = "redis.pool.max-active";
    /**
     * Redis最大空闲时间
     */
    public static final String REDIS_POOL_MAX_IDLE = "redis.pool.max-idle";
    /**
     * 最大等待时间
     */
    public static final String REDIS_POOL_MAX_WAIT = "redis.pool.max-wait";
    /**
     * 最小空闲时间
     */
    public static final String REDIS_POOL_MIN_IDLE = "redis.pool.min-idle";
    /**
     * 追踪日志是否开启
     */
    public static final String TRACE_LOG_ENABLED = "trace.log.enabled";
    /**
     * 追踪日志存储类型
     */
    public static final String TRACE_LOG_STORAGE_TYPE = "trace.log.storage.type";
    /**
     * 追踪日志不存储的URL
     */
    public static final String TRACE_LOG_EXCLUDE_URL = "trace.log.exclude.url";
    /**
     * 不存储的URL前缀
     */
    public static final String TRACE_LOG_EXCLUDE_URL_PREFIX = "trace.log.exclude.url.prefix";
    /**
     * 存储的URL列表
     */
    public static final String TRACE_LOG_INCLUDE_URL = "trace.log.include.url";
    /**
     * 存储的URL前缀
     */
    public static final String TRACE_LOG_INCLUDE_URL_PREFIX = "trace.log.include.url.prefix";
    /**
     * 追踪日志保留的天数
     */
    public static final String TRACE_LOG_REMAIN_DAYS = "trace.log.remain.days";

    /**
     * 异步任务线程池的最大大小
     */
    public static final String ASYNC_MAX_POOL_SIZE = "thread.pool.async.task.max-pool-size";

    /**
     * 异步核心线程池的大小
     */
    public static final String ASYNC_CORE_POOL_SIZE = "thread.pool.async.task.core-pool-size";

    /**
     * 异步任务线程池的等待任务队列大小
     */
    public static final String ASYNC_QUEUE_CAPACITY = "thread.pool.async.task.queue-capacity";

    /**
     * 异步任务线程池的保持活跃状态的时间
     */
    public static final String ASYNC_KEEPALIVE_SECONDS = "thread.pool.async.task.keepalive-seconds";

    /**
     * 异步任务线程池是否允许核心线程超时
     */
    public static final String ASYNC_ALLOW_CORE_THREAD_TIMEOUT = "thread.pool.async.task.allow-core-thread-timeout";

    /**
     * 异步任务线程池拒绝策略类的名称
     */
    public static final String ASYNC_REJECTED_HANDLER_CLASS = "thread.pool.async.task.rejected-handler-class";

    /**
     * 异步任务线程池拒绝策略方法
     */
    public static final String ASYNC_REJECTED_HANDLER = "thread.pool.async.task.rejected-handler";

    /**
     * 是否启用http代理
     */
    public static final String HTTP_PROXY_ENABLED = "http.proxy.enabled";
    /**
     * 代理主机
     */
    public static final String HTTP_PROXY_HOST = "http.proxy.host";
    /**
     * 代理端口
     */
    public static final String HTTP_PROXY_PORT = "http.proxy.port";
    /**
     * HTTP连接池最大数量
     */
    public static final String HTTP_POOL_MAX_SIZE = "http.pool.maxSize";
    /**
     * 连接超时(毫秒数)
     */
    public static final String HTTP_CLIENT_CONNECT_TIMEOUT_MS = "http.client.connect.timeout.ms";
    /**
     * 读取超时(毫秒数）
     */
    public static final String HTTP_CLIENT_READ_TIMEOUT_MS = "http.client.read.timeout.ms";
    /**
     * jsoup抓取网页超时时间
     */
    public static final String JSOUP_TIMEOUT_MS = "jsoup.timeout.ms";
    /**
     * jsoup抓取网页重试场景
     */
    public static final String HTTP_CLIENT_RETRY_SCENE = "http.client.retry.scene";
    /**
     * jsoup抓取网页重试次数
     */
    public static final String JSOUP_RETRY_TIMES = "jsoup.retry.times";

    /**
     * http客户端重试次数
     */
    public static final String HTTP_CLIENT_RETRY_TIMES = "http.client.retry.times";

}
