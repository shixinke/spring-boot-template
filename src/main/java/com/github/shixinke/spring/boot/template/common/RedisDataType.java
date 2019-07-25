package com.github.shixinke.spring.boot.template.common;

/**
 * Redis数据类型
 * @author shixinke
 */
public enum RedisDataType {
    /**
     * 字符串类型
     */
    STRING(1),
    /**
     * hash类型
     */
    HASH(2),
    /**
     * list类型
     */
    LIST(3),
    /**
     * 集合类型
     */
    SET(4),
    /**
     * 有序集合类型
     */
    SORTED_SET(5);
    private int code;
    RedisDataType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
