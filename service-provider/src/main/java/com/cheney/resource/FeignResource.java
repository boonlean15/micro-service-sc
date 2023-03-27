package com.cheney.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author cheney
 * @date 2023/3/20 11:06
 */
@RestController
@RequestMapping
@Slf4j
public class FeignResource {
    @Resource
    EurekaClientConfigBean eurekaClientConfigBean;

    @GetMapping("/eurekaClientConfig")
    public String serviceList(String queryStr){
        log.info("queryStr === " + queryStr);
        Map<String, String> serviceUrl = eurekaClientConfigBean.getServiceUrl();
        return serviceUrl.toString();
    }
}
