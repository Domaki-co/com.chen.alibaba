package com.chen.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String server_port;
    @GetMapping(value = "/payment/nacos/{id}")
    public String echo(@PathVariable Integer id) {
        return "Hello Nacos Discovery " +server_port+"\t id:"+ id;
    }
}
