package com.github.shixinke.spring.boot.template.component;

import com.github.shixinke.spring.boot.template.common.AppConfigKeys;
import com.github.shixinke.spring.boot.template.common.Defaults;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * 线程池组件
 * @author shixinke
 */
@Component
public class ThreadPoolComponent {

    @Resource
    private AppConfigComponent configComponent;

    @Bean(name ="asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = configComponent.getProperty(AppConfigKeys.ASYNC_MAX_POOL_SIZE, Integer.class, processors * Defaults.MULTIPLE_NUMBER_OF_PROCESSORS + 2);
        int corePoolSize = configComponent.getProperty(AppConfigKeys.ASYNC_CORE_POOL_SIZE, Integer.class,  Defaults.MULTIPLE_NUMBER_OF_PROCESSORS);
        int queueCapacity = configComponent.getProperty(AppConfigKeys.ASYNC_QUEUE_CAPACITY, Integer.class, Defaults.THREAD_POOL_QUEUE_CAPACITY);
        int keepAliveSeconds = configComponent.getProperty(AppConfigKeys.ASYNC_KEEPALIVE_SECONDS, Integer.class, Defaults.THREAD_POOL_KEEP_ALIVE_SECONDS);
        boolean allowCoreThreadTimeout = configComponent.getProperty(AppConfigKeys.ASYNC_ALLOW_CORE_THREAD_TIMEOUT, Boolean.class, Defaults.THREAD_POOL_ALLOW_CORE_THREAD_TIMEOUT);
        String beanName = "asyncTaskExecutor";
        return threadPoolTaskExecutor(maxPoolSize, corePoolSize, queueCapacity, keepAliveSeconds, allowCoreThreadTimeout, null, beanName);
    }

    private ThreadPoolTaskExecutor threadPoolTaskExecutor(int maxPoolSize, int corePoolSize, int queueCapacity, int keepAliveSeconds, boolean allowCoreThreadTimeOut, RejectedExecutionHandler rejectedExecutionHandler, String beanName) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        if (rejectedExecutionHandler != null) {
            threadPoolTaskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        }
        if (!StringUtils.isEmpty(beanName)) {
            threadPoolTaskExecutor.setBeanName(beanName);
        }
        return threadPoolTaskExecutor;
    }
}
