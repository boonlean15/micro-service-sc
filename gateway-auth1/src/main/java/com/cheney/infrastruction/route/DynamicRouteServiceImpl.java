package com.cheney.infrastruction.route;

import com.alibaba.fastjson.JSON;
import com.cheney.domain.route.GatewayFilterDefinition;
import com.cheney.domain.route.GatewayPredicateDefinition;
import com.cheney.domain.route.GatewayRouteDefinition;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 结合配置中心实现动态路由
 * @author cheney
 * @date 2023/3/7 18:31
 */
//@Component
@Slf4j
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Resource
    RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @ApolloConfigChangeListener(value = "TEST1.gateway-config")
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean gatewayPropertiesChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            if (changedKey.startsWith("spring.cloud.gateway.routes")) {
                gatewayPropertiesChanged = true;
                break;
            }
        }

        if (gatewayPropertiesChanged) {
            refreshGatewayProperties(changeEvent);
        }
    }

    private void refreshGatewayProperties(ConfigChangeEvent changeEvent) {
        log.info("Refreshing gateway properties!");

        /**
         * rebind configuration beans, e.g. GatewayProperties
         * @see org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder#onApplicationEvent
         */
        this.publisher.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        /**
         * refresh routes
         * @see org.springframework.cloud.netflix.zuul.ZuulServerAutoConfiguration.ZuulRefreshListener#onApplicationEvent
         */
        for (String changedKey : changeEvent.changedKeys()) {
            //刷新服务路由
            if (changedKey.startsWith("spring.cloud.gateway.routes.serviceRoute")) {
                ConfigChange change = changeEvent.getChange(changedKey);
                String newValue = change.getNewValue();
                log.info("spring.cloud.gateway.routes.predicates.path newValue = " + newValue + "  oldValue = " + change.getOldValue());
                GatewayRouteDefinition gatewayRouteDefinition = JSON.parseObject(newValue, GatewayRouteDefinition.class);
                RouteDefinition routeDefinition = assembleRouteDefinition(gatewayRouteDefinition);
                routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                this.publisher.publishEvent(new RefreshRoutesEvent(this));
            }
        }
        log.info("gateway properties refreshed!");
    }

    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwDefinition) {

        RouteDefinition definition = new RouteDefinition();

        // ID
        definition.setId(gwDefinition.getId());

        // Predicates
        List<PredicateDefinition> pdList = new ArrayList<>();
        for (GatewayPredicateDefinition gpDefinition: gwDefinition.getPredicates()) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);

        // Filters
        List<FilterDefinition> fdList = new ArrayList<>();
        for (GatewayFilterDefinition gfDefinition: gwDefinition.getFilters()) {
            FilterDefinition filter = new FilterDefinition();
            filter.setArgs(gfDefinition.getArgs());
            filter.setName(gfDefinition.getName());
            fdList.add(filter);
        }
        definition.setFilters(fdList);

        // URI
        URI uri = UriComponentsBuilder.fromUriString(gwDefinition.getUri()).build().toUri();
        definition.setUri(uri);

        return definition;
    }

    /**
     * 添加路由
     * @param definition
     * @return
     */
    public String add(RouteDefinition definition){
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /**
     * 更新路由 - 先删除后增加
     * @param definition
     * @return
     */
    public String update(RouteDefinition definition){
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route  routeId: "+definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }
    }

    /**
     * 删除路由
     * @param id
     * @return
     */
    public String delete(String id){
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            return "delete success";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }
    }

    /**
     * 删除路由 - v1
     * @param id
     * @return
     */
    public Mono<ResponseEntity<Object>> deleteV1(String id){
        return this.routeDefinitionWriter.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build()));
    }
}
