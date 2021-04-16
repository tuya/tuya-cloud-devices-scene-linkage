package com.tuya.cloud.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @className: BaseConnectionConfig
 * @description: 自动化操作配置类
 * @author: 布鲁 龙盼盼 
 * @date: 2021/4/8
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "tuya.cloud.auto")
public class AutomationConfig {
    private String homeId;
    private String addUri;
    private String enableUri;
    private String deleteUri;
}
