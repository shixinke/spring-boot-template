package com.github.shixinke.spring.boot.template.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CommonUtil {


    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class.getCanonicalName());

    /**
     * 获取总页数
     * @param total
     * @param pageSize
     * @return
     */
    public static Long getPages(Long total, Integer pageSize) {
        long pages = 0L;
        if (total == null || total == 0 || pageSize == null || pageSize == 0) {
            return pages;
        }
        if (total <= pageSize) {
            return 1L;
        }
        long remain = total % pageSize;
        pages = total / pageSize;
        if (remain != 0) {
            pages ++;
        }
        return pages;
    }

    /**
     * 获取总页数
     * @param total
     * @param pageSize
     * @return
     */
    public static int getPages(int total, Integer pageSize) {
        return getPages(Long.valueOf(total), pageSize).intValue();
    }

    public static <T>  T parseValue(String value, Class<T> clazz) {
        Object o = value;
        try {
            if (value == null ) {
                return null;
            }
            if (clazz == Long.class) {
                o = Long.valueOf(value);
            } else if (clazz == Integer.class) {
                o =  Integer.valueOf(value);
            } else if (clazz == Short.class) {
                o = Short.valueOf(value);
            } else if (clazz == Boolean.class) {
                o = Boolean.valueOf(value);
            } else if (clazz == Byte.class) {
                o = Byte.valueOf(value);
            } else if (clazz == Float.class) {
                o = Float.valueOf(value);
            } else if (clazz == Double.class) {
                o = Double.valueOf(value);
            } else if (clazz == BigDecimal.class) {
                o = new BigDecimal(value);
            } else if (clazz == BigInteger.class) {
                o = new BigInteger(value);
            } else  {
                o = JSON.parseObject(value, clazz);
            }
            return (T) o;
        } catch (Exception ex) {
            LOGGER.error("解析值出错");
        }
        return null;
    }

    public static <T>  List<T> parseValue(List<String> values, Class<T> clazz) {
        List<T> list = new ArrayList<>(values.size());
        for (String v : values) {
            list.add(parseValue(v, clazz));
        }
        return list;
    }

    public static <T> Set<T> parseValue(Set<String> values, Class<T> clazz) {
        Set<T> sets = new HashSet<>(values.size());
        for (String v : values) {
            sets.add(parseValue(v, clazz));
        }
        return sets;
    }
}
