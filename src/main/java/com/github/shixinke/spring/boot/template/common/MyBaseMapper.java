package com.github.shixinke.spring.boot.template.common;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础mapper(基于tk.mybatis)
 * @author shixinke
 * @param <T>
 */
public interface MyBaseMapper<T> extends BaseMapper<T>, MySqlMapper<T> {
}
