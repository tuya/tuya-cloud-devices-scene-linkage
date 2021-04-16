package com.tuya.cloud.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @className: BaseConnectionConfig
 * @description: token相关配置
 * @author: 布鲁 龙盼盼 
 * @date: 2021/4/8
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "tuya.cloud.token")
public class TokenConfig {
    private String tokenUri;

}
