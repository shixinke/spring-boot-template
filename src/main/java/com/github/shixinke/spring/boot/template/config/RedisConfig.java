package com.github.shixinke.spring.boot.template.config;

import com.github.shixinke.spring.boot.template.common.AppConfigKeys;
import com.github.shixinke.spring.boot.template.common.Defaults;
import com.github.shixinke.spring.boot.template.component.AppConfigComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis配置
 * @author shixinke
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Resource
    private AppConfigComponent appConfigComponent;


    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(appConfigComponent.getProperty(AppConfigKeys.REDIS_POOL_MAX_IDLE, Integer.class, Defaults.REDIS_POOL_MAX_IDLE));
        config.setMinIdle(appConfigComponent.getProperty(AppConfigKeys.REDIS_POOL_MIN_IDLE, Integer.class, Defaults.REDIS_POOL_MIN_IDLE));
        config.setMaxWaitMillis(appConfigComponent.getProperty(AppConfigKeys.REDIS_POOL_MAX_WAIT, Integer.class,  Defaults.REDIS_POOL_MAX_WAIT));
        config.setMaxTotal(appConfigComponent.getProperty(AppConfigKeys.REDIS_POOL_MAX_ACTIVE, Integer.class, Defaults.REDIS_POOL_MAX_ACTIVE));
        String password = appConfigComponent.getProperty(AppConfigKeys.REDIS_PASSWORD);
        String host = appConfigComponent.getProperty(AppConfigKeys.REDIS_HOST, Defaults.REDIS_HOST);
        int port = appConfigComponent.getProperty(AppConfigKeys.REDIS_PORT, Integer.class, Defaults.REDIS_PORT);
        int timeout = appConfigComponent.getProperty(AppConfigKeys.REDIS_CONNECT_TIMEOUT, Integer.class, Defaults.REDIS_CONNECT_TIMEOUT);
        if (StringUtils.isBlank(password)) {
            return new JedisPool(config, host, port, timeout);
        } else {
            return new JedisPool(config, host, port, timeout, password);
        }

    }

    @Bean
    public Jedis jedis() {
        try {
            return jedisPool().getResource();
        } catch (Exception ex) {
            log.error("jedis连接失败:", ex);
            return new Jedis();
        }

    }
}
