package com.cheney.infrastruction.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @author cheney
 * @date 2023/3/8 11:21
 */
@Configuration
@EnableApolloConfig(value = "application", order = 10)
public class AppConfig {
}
