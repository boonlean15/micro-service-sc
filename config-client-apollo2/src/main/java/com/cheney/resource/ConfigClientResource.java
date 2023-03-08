package com.cheney.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheney
 * @date 2023/3/8 11:21
 */
@RestController
@RequestMapping("configConsumer")
@RefreshScope
public class ConfigClientResource {

    @Value("${config_info_new}")
    private String config;

    @RequestMapping("/getConfigInfo")
    public String getConfigInfo(){
        return config;
    }
}
