package com.github.shixinke.spring.boot.template.data.bean;

import lombok.Data;


/**
 * 追踪日志实体
 * @author shixinke
 */
@Data
public class TraceLog {
    private String traceId;
    private String url;
    private String params;
    private String response;
    private String headers;
    private String cookies;
    private int code;
    private int statusCode;
    private Long executeTime;
    private Long createTime;
}
