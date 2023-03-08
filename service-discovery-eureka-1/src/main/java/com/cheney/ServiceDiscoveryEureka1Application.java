package com.cheney;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author cheney
 * @date 2023/3/7 14:38
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryEureka1Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryEureka1Application.class, args);
    }
}
