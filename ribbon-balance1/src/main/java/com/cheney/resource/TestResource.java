package com.cheney.resource;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author cheney
 * @date 2023/3/9 16:49
 */
@RestController
public class TestResource {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient lbClient;

    @GetMapping("/add")
    public String adda(Integer a, Integer b) {
        String result = restTemplate
                .getForObject("http://SERVICE-PROVIDER/add?a=" + a + "&b=" + b, String.class);
        System.out.println(result);
        return result;
    }

    @GetMapping("/add1")
    public void add1(Integer a, Integer b) {
        ServiceInstance instance = this.lbClient.choose("service-provider");
        System.out.println(instance.getHost()+":"+instance.getPort());
    }

    @GetMapping("/add2")
    public void add2(Integer a, Integer b) {
        ServiceInstance instance = this.lbClient.choose("service-provider");
        System.out.println(instance.getHost()+":"+instance.getPort());
    }

}
