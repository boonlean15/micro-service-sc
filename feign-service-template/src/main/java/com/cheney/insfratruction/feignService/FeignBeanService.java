package com.cheney.insfratruction.feignService;

import com.cheney.insfratruction.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author cheney
 * @date 2023/3/20 10:55
 */
@FeignClient(name = "service-provider", configuration = FeignServiceConfig.class)
public interface FeignBeanService {

//    @RequestMapping(value = "/eurekaClientConfig", method = RequestMethod.GET)
//    ResponseEntity<byte[]> serviceList(@RequestParam("q") String queryStr);

    @RequestMapping(value = "/eurekaClientConfig", method = RequestMethod.GET)
    String serviceList(@RequestParam("q") String queryStr);

}
