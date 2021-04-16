package com.tuya.cloud.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @className: BaseConnectionConfig
 * @description: 基础配置
 * @author: 布鲁 龙盼盼 
 * @date: 2021/4/8
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "tuya.cloud.base")
public class BaseConfig {
    private String domain;
    private String clientId;
    private String secret;

}
