package com.cheney.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cheney
 * @date 2023/3/8 10:14
 */
@RestController
@RequestMapping
public class TestResource {

    @GetMapping("/v1")
    public String v1(){
        return "v1";
    }

    @GetMapping("/v2")
    public String v2(){
        return "v2";
    }

    @GetMapping("/add")
    public String add(Integer a, Integer b, HttpServletRequest request){
        return " From Port: "+ request.getServerPort() + ", Result: " + (a + b);
    }
}
