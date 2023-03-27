package com.cheney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author cheney
 * @date 2023/3/20 10:54
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignServiceTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignServiceTemplateApplication.class, args);
    }
}
