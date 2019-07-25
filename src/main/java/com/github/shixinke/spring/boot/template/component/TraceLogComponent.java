package com.github.shixinke.spring.boot.template.component;

import com.github.shixinke.spring.boot.template.common.AppConfigKeys;
import com.github.shixinke.spring.boot.template.common.Defaults;
import com.github.shixinke.spring.boot.template.data.bean.TraceLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 追踪日志组件
 * @author shixinke
 */
@Component
@Slf4j
public class TraceLogComponent {

    @Resource
    private AppConfigComponent configComponent;

    public void log(HttpServletRequest request, HttpServletResponse response) {
        TraceLog traceLog = new TraceLog();
        traceLog.setUrl(request.getRequestURI());
        log(traceLog);
    }

    @Async("asyncTaskExecutor")
    public void log(TraceLog traceLog) {
        Boolean traceLogEnabled = configComponent.getProperty(AppConfigKeys.TRACE_LOG_ENABLED, Boolean.class, Defaults.TRACE_LOG_ENABLED);
        if (traceLogEnabled) {
            List<String> includeUrlList = configComponent.getPropertyList(AppConfigKeys.TRACE_LOG_INCLUDE_URL);
            if (!includeUrlList.isEmpty() && !includeUrlList.contains(traceLog.getUrl())) {
                return;
            }
            List<String> includePrefixList = configComponent.getPropertyList(AppConfigKeys.TRACE_LOG_INCLUDE_URL_PREFIX);
            if (!includePrefixList.isEmpty() ) {
                boolean included = false;
                for (String includePrefix : includePrefixList) {
                    if (traceLog.getUrl().startsWith(includePrefix)) {
                        included = true;
                        break;
                    }
                }
                if (!included) {
                    return;
                }
            }
            List<String> excludeUrlList = configComponent.getPropertyList(AppConfigKeys.TRACE_LOG_EXCLUDE_URL);
            if (!excludeUrlList.isEmpty() && excludeUrlList.contains(traceLog.getUrl())) {
                return;
            }
            List<String> excludePrefixList = configComponent.getPropertyList(AppConfigKeys.TRACE_LOG_EXCLUDE_URL_PREFIX);
            if (!excludePrefixList.isEmpty() ) {
                for (String excludePrefix : excludePrefixList) {
                    if (traceLog.getUrl().startsWith(excludePrefix)) {
                        return;
                    }
                }
            }
            log.info("url={};params={};response={}", traceLog.getUrl(), traceLog.getParams(), traceLog.getResponse());
        }
    }

}
