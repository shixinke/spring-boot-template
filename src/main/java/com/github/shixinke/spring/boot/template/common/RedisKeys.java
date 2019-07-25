package com.github.shixinke.spring.boot.template.common;

/**
 * Redis缓存键等定义
 * @author shixinke
 */
public enum RedisKeys {
    ;
    private String key;
    private Integer expires;
    private String remark;
    private RedisDataType dataType;

    RedisKeys(String key, Integer expires, String remark, RedisDataType redisDataType) {
        this.key = key;
        this.expires = expires;
        this.remark = remark;
        this.dataType = redisDataType;
    }

    public String getKey() {
        return key;
    }

    public Integer getExpires() {
        return expires;
    }

    public String getRemark() {
        return remark;
    }

    public RedisDataType getDataType() {
        return dataType;
    }
}
