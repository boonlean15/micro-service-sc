package com.cheney;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author cheney
 * @date 2023/3/7 18:23
 */
@EnableApolloConfig
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayAuth1Application {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAuth1Application.class, args);
    }

}
