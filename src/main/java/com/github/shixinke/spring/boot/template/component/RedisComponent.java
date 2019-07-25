package com.github.shixinke.spring.boot.template.component;

import com.github.shixinke.spring.boot.template.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Redis操作类
 * @author shixinke
 */
@Component
@Slf4j
public class RedisComponent {

    @Resource
    private Jedis redisClient;

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        try {
           String result = redisClient.set(key, value);
           return isSuccess(result);
        } catch (Exception ex) {
            log.error("redis set exception:key={}", key, ex);
        }
        return false;
    }

    /**
     * 写入缓存设置失效时间
     * @param key
     * @param value
     * @param expireTime
     */
    public boolean setEx(final String key, String value, int expireTime) {
        try {
            String result = redisClient.setex(key, expireTime, value);
            return isSuccess(result);
        } catch (Exception ex) {
            log.error("redis setEx exception:key={}", key, ex);
        }
        return false;
    }

    /**
     * 当不存在时存入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean setNx(final String key, String value) {
        try {
            Long result = redisClient.setnx(key, value);
            return result > 0;
        } catch (Exception ex) {
            log.error("redis setNx exception:key={}", key, ex);
        }
        return false;
    }


    /**
     * 设置键的有效期
     * @param key
     * @param expire
     */
    public boolean expire(final String key, Integer expire){
        try {
            Long result = redisClient.expire(key, expire);
            return result > 0;
        } catch (Exception ex) {
            log.error("redis expire exception:key={}", key, ex);
        }
        return false;
    }

    /**
     * 获取键值
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(final String key, Class<T> clazz) {
        String v = get(key);
        return CommonUtil.parseValue(v, clazz);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            return redisClient.get(key);
        } catch (Exception ex) {
            log.error("redis get exception:key={}", key, ex);
        }
        return null;
    }

    /**
     * 从列表中弹出
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T lpop(final String key, Class<T> clazz) {
        try {
            String v = redisClient.lpop(key);
            return CommonUtil.parseValue(v, clazz);
        } catch (Exception ex) {
            log.error("redis lpop exception:key={}", key, ex);
        }
        return null;
    }

    /**
     * 从集合中弹出
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T spop(final String key, Class<T> clazz) {
        try {
            String v = redisClient.spop(key);
            return CommonUtil.parseValue(v, clazz);
        } catch (Exception ex) {
            log.error("redis spop exception:key={}", key, ex);
        }
        return null;
    }

    /**
     * 批量查询缓存
     * @param keys
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> mGet(final List<String> keys, Class<T> clazz) {
        try {
            List<String> values = redisClient.mget(keys.toArray(new String[keys.size()]));
            return CommonUtil.parseValue(values, clazz);
        } catch (Exception ex) {
            log.error("redis mget exception:keys={}", keys, ex);
        }
        return null;
    }

    /**
     * 批量设置缓存
     * @param values
     */
    public boolean mSet(final Map<String, Object> values) {
        try {
            List<String> dataList = new ArrayList<>(values.size() * 2);
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                dataList.add(entry.getKey());
                dataList.add(String.valueOf(entry.getValue()));
            }
            String result = redisClient.mset(dataList.toArray(new String[dataList.size()]));
            return isSuccess(result);
        } catch (Exception ex) {
            log.error("redis mset exception:values={}", values, ex);
        }
        return false;
    }

    /**
     * 删除缓存
     * @param key
     */
    public boolean del(String key) {
        try {
            long result = redisClient.del(key);
            return result > 0;
        } catch (Exception ex) {
            log.error("redis del exception:key={}", key, ex);
        }
        return false;
    }

    /**
     * 删除缓存
     * @param keys
     * @return
     */
    public boolean del(List<String> keys) {
        try {
            long result = redisClient.del(keys.toArray(new String[keys.size()]));
            return result > 0;
        } catch (Exception ex) {
            log.error("redis del exception:keys={}", keys, ex);
        }
        return false;
    }

    /**
     * 自增操作
     * @param key
     * @return
     */
    public Long incr(String key) {
        try {
            return redisClient.incr(key);
        } catch (Exception ex) {
            log.error("redis incr exception:key={}", key, ex);
        }
        return 0L;
    }



    /**
     * 向列表缓存添加数据
     * @param key
     * @param value
     */
    public long lpush(final String key, final Object value) {
        try {
            return redisClient.lpush(key, String.valueOf(value));
        } catch (Exception ex) {
            log.error("redis lpush exception:key={}", key, ex);
        }
        return 0L;
    }

    /**
     * 从右侧向列表插入数据
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> long rpush(final String key, T value) {
        try {
            return redisClient.rpush(key, String.valueOf(value));
        } catch (Exception ex) {
            log.error("redis rpush exception:key={}", key, ex);
        }
        return 0L;
    }


    /**
     * 是否在有序集合中
     * @param key
     * @param member
     * @param <T>
     * @return
     */
    public <T> boolean zIsMember(final String key, String member) {
        try {
            Long index =  redisClient.zrank(key, member);
            return index != null ;
        } catch (Exception ex) {
            log.error("redis zrank exception:key={};member={}", key);
        }
        return false;
    }


    /**
     * 获取集合成员的个数
     * @param key
     * @return
     */
    public Long zCard(final String key) {
        try {
            return redisClient.zcard(key);
        } catch (Exception ex) {
            log.error("redis zcard exception:key={};member={}", key);
        }
        return 0L;
    }

    /**
     * 向集合缓存添加数据
     * @param key
     * @param value
     * @param <T>
     */
    public <T> boolean sAdd(final String key, T value) {
        try {
            long result =  redisClient.sadd(key, String.valueOf(value));
            return result > 0;
        } catch (Exception ex) {
            log.error("redis sadd exception:key={};member={}", key);
        }
        return false;
    }

    /**
     * 向集合缓存批量添加数据
     * @param key
     * @param values
     * @param <T>
     */
    public <T> boolean sAdd(final String key, List<T> values) {
        try {
            String[] members = values.toArray(new String[values.size()]);
            long result =  redisClient.sadd(key, members);
            return result > 0;
        } catch (Exception ex) {
            log.error("redis sadd exception:key={};member={}", key);
        }
        return false;
    }


    /**
     * 删除集合中的某个成员
     * @param key
     * @param value
     * @param <T>
     */
    public <T> boolean sRem(final String key, T value) {
        try {
            long result =  redisClient.srem(key, String.valueOf(value));
            return result > 0;
        } catch (Exception ex) {
            log.error("redis srem exception:key={};member={}", key);
        }
        return false;
    }

    /**
     * 删除集合中的某个成员
     * @param key
     * @param values
     * @param <T>
     */
    public <T> boolean sRem(final String key, List<T> values) {
        try {
            String[] members = values.toArray(new String[values.size()]);
            long result =  redisClient.srem(key, members);
            return result > 0;
        } catch (Exception ex) {
            log.error("redis srem exception:key={};member={}", key);
        }
        return false;
    }

    /**
     * 返回集合所有成员
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Set<T> sMembers(final String key, Class<T> clazz) {
        Set<T> members = new HashSet<T>();
        try {
            Set<String> values =  redisClient.smembers(key);
            return CommonUtil.parseValue(values, clazz);
        } catch (Exception ex) {
            log.error("redis smembers exception:key={};member={}", key);
        }
        return members;
    }

    /**
     * 随机返回集合中的某个成员
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T sRandMember(final String key, Class<T> clazz) {
        try {
            String v =  redisClient.srandmember(key);
            return CommonUtil.parseValue(v, clazz);
        } catch (Exception ex) {
            log.error("redis srandmember exception:key={};member={}", key);
        }
        return null;
    }

    /**
     * 获取集合成员的个数
     * @param key
     * @return
     */
    public Long sCard(final String key) {
        try {
            return redisClient.scard(key);
        } catch (Exception ex) {
            log.error("redis scard exception:key={};member={}", key);
        }
        return 0L;
    }

    /**
     * 检查某个值是否为集合中的成员
     * @param key
     * @param member
     * @return
     */
    public <T> boolean sIsMember(final String key, T member) {
        try {
            return redisClient.sismember(key, String.valueOf(member));
        } catch (Exception ex) {
            log.error("redis sIsMember exception:key={};member={}", key, member);
        }
        return false;
    }



    /**
     * 通过SETNX试图获取一个锁
     *
     * @param expire 存活时间(秒)
     */
    public boolean acquire(final String key, final int expire) {
        boolean success = false;
        try {
            long value = System.currentTimeMillis() + expire * 1000 + 1;
            Long result = redisClient.setnx(key, String.valueOf(value));
            if (result > 0) {
                if (expire > 0) {
                    try {
                        redisClient.expire(key, expire);
                    } catch (Throwable e) {
                        log.error("expire exception:key={}:", key, e);
                    }
                }
                success = true;
            } else {
                /* 当前锁过期时间 */
                Long oldValue = get(key, Long.class);
                /* 超时 */
                if (oldValue != null && oldValue < System.currentTimeMillis()) {
                    /* 查看是否有并发 */
                    String oldValueAgain = redisClient.getSet(key, String.valueOf(value));
                    /* 获取锁成功 */
                    if (oldValue.equals(Long.valueOf(oldValueAgain))) {
                        if (expire > 0) {
                            try {
                                redisClient.expire(key, expire);
                            } catch (Throwable e) {
                                log.error("expire exception :key={}", key, e);
                            }
                        }
                        success = true;
                    } else {
                        /* 已被其他进程捷足先登了 */
                        success = false;
                    }
                } else {
                    /* 未超时，则直接返回失败 */
                    success = false;
                }
            }
        } catch (Throwable e) {
            log.error("redis acquire exception:key={}", key, e);
        }
        return success;
    }



    /**
     * 释放锁
     */
    public boolean release(final String key) {
        try {
            String lockTime = redisClient.get(key);
            if (StringUtils.isBlank(lockTime)) {
                return false;
            }
            long getValue = Long.parseLong(lockTime);
            if (System.currentTimeMillis() < getValue) {
                Long result = redisClient.del(SafeEncoder.encode(key));
                return result > 0;
            }
        } catch (Exception e) {
            log.error("redis release exception:key={}", key, e);
        }
        return false;
    }

    /**
     * 是否操作成功
     * @param result
     * @return
     */
    private boolean isSuccess(String result) {
        String ok = "OK";
        return ok.equalsIgnoreCase(result);
    }
}
