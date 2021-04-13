package com.chen.alibaba.controller;

import com.chen.springcloud.entity.CommonResult;
import com.chen.springcloud.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    public static HashMap<Long, Payment> hashMap=new HashMap();
    static {
        hashMap.put(1L,new Payment(1L,"zxcvbnn"));
        hashMap.put(2L,new Payment(2L,"qwertyu"));
        hashMap.put(3L,new Payment(3L,"asdfghj"));
    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult pay(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> commonResult = new CommonResult(200, "from mysql ; port:" + serverPort, payment);
        return commonResult;
    }
}
