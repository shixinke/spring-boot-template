package com.github.shixinke.spring.boot.template.common;

/**
 * 网络状态码定义
 * @author shixinke
 */
public enum NetworkStatusCodes {
    HTTP_OK(200, "success"),
    HTTP_MOVED_PERMANENTLY(301, "页面重定向"),
    HTTP_MOVED_TEMPORARILY(302, "页面临时跳转"),
    HTTP_BAD_REQUEST(400, "请求出错"),
    HTTP_UNAUTHORIZED(401, "未授权，登录失效"),
    HTTP_FORBIDDEN(403, "无权限访问"),
    HTTP_NOT_FOUND(404, "请求地址不存在"),
    HTTP_NOT_ALLOWED(405, "请求方式不正确"),
    HTTP_INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    HTTP_BAD_GATEWAY(502, "网关错误"),
    HTTP_SERVICE_UNAVAILABLE(503, "服务不可用"),
    HTTP_GATEWAY_TIMEOUT(504, "网关超时")
    ;

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误提示信息
     */
    private String message;

    NetworkStatusCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessageByCode(int code) {
        for (NetworkStatusCodes e : values()) {
            if (code == e.getCode()) {
                return e.getMessage();
            }
        }
        return null;
    }
}
