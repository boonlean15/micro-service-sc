package com.cheney.resource;

import com.cheney.insfratruction.feignService.FeignBeanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author cheney
 * @date 2023/3/20 11:08
 */
@RestController
@RequestMapping("/feign")
@Slf4j
public class EurekaResource {

    @Resource
    FeignBeanService feignBeanService;

    @GetMapping("/eurekaClientConfig")
    public String serviceList(String q){
        log.info("q === " + q);
        return feignBeanService.serviceList(q);
    }

}
