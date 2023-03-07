package com.cheney.domain.route;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cheney
 * @date 2023/3/7 18:26
 */
@Data
public class GatewayFilterDefinition {
    /**
     * Filter Name
     */
    private String name;

    /**
     * 对应的路由规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
