package com.cheney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author cheney
 * @date 2023/3/7 14:39
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryEureka3Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryEureka3Application.class, args);
    }
}
