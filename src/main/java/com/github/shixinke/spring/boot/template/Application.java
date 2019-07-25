package com.github.shixinke.spring.boot.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.logging.Logger;

/**
 * Steam-tracker-api 启动类
 * @author shixinke
 */
@MapperScan(basePackages = "com.github.shixinke.spring.boot.template.dao.mapper")
@SpringBootApplication(scanBasePackages = {"com.github.shixinke.spring.boot.template"})
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getCanonicalName());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.info("application 启动成功");
    }

}
