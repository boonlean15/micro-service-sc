package com.cheney.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取配置信息资源
 * @author cheney
 * @date 2023/3/7 16:44
 */
@RestController
@RequestMapping("configConsumer")
@RefreshScope
public class ConfigClientResource {

    @Value("${config_info}")
    private String config;

    @RequestMapping("/getConfigInfo")
    public String getConfigInfo(){
        return config;
    }
}
