package com.cheney.infrastruction.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @author cheney
 * @date 2023/3/8 16:02
 */
@Configuration
@EnableApolloConfig(value = "TEST1.sd-eureka-peer1", order = 10)
public class AppConfig {
}
