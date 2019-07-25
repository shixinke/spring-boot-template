package com.github.shixinke.spring.boot.template.common;

import com.shixinke.utils.web.common.Errors;

/**
 * 公共常量定义
 * @author shixinke
 */
public class Constants {
    /**
     * 请求头中accessToken的键名
     */
    public static final String ACCESS_TOKEN = "X-ACCESS-TOKEN";

    /**
     * 追踪日志名称
     */
    public static final String TRACE_LOG_ID = "trace_id";

    /**
     * 配置中值的分隔符
     */
    public static final String PROPERTY_VALUE_SEPARATOR = ",";


    /**
     * 重试错误码前缀
     */
    public static final String RETRY_ERROR_PREFIX = "error";

    /**
     * 正常状态
     */
    public static final Integer NORMAL_STATUS = 1;
    /**
     * 非正常状态
     */
    public static final Integer ABNORMAL_STATUS = 0;
    /**
     * 未知状态
     */
    public static final Integer UNKNOWN_STATUS = -1;

}
