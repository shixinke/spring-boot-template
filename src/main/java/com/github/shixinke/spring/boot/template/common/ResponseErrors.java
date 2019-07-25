package com.github.shixinke.spring.boot.template.common;

/**
 * 业务错误定义(Errors的扩展)
 * @author shixinke
 */
public enum ResponseErrors {

    ;

    ResponseErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误提示信息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessageByCode(int code) {
        for (ResponseErrors e : values()) {
            if (code == e.getCode()) {
                return e.getMessage();
            }
        }
        return null;
    }
}
