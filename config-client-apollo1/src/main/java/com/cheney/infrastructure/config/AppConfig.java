package com.cheney.infrastructure.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

/**
 * 开启Apollo配置信息
 * @author cheney
 * @date 2023/3/7 16:41
 */
@Configuration
@EnableApolloConfig(value = "application", order = 10)
public class AppConfig {
}
