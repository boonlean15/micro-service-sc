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
@EnableApolloConfig
public class ServiceDiscoveryEureka2Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryEureka2Application.class, args);
    }
}
