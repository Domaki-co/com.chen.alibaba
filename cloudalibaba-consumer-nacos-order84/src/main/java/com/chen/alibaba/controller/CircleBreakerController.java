package com.chen.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.alibaba.service.PaymentService;
import com.chen.springcloud.entity.CommonResult;
import com.chen.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {
    public static String SERVER_URL="http://nacos-payment-provider";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback",fallback = "handleFallback")
    //@SentinelResource(value = "fallback",blockHandler = "blockHandle") //只是用blockHandle 限流兜底
    @SentinelResource(value = "fallback",blockHandler = "BlockHandle",fallback = "handleFallback",
    exceptionsToIgnore = {IllegalArgumentException.class}
    ) //同时使用
    public CommonResult<Payment> fallback(@PathVariable("id")Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVER_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id==4){
            throw new IllegalArgumentException("IllegalArgumentException，非法参数异常");
        }else if (result.getData()==null){
            throw new NullPointerException("NullPointerException,空指针异常");
        }
        return result;
    }
    public CommonResult handleFallback(@PathVariable Long id,Throwable e){
        Payment payment = new Payment(id, null);
        return new CommonResult(444,"兜底异常，exception内容： "+e.getMessage(),payment);
    }

    public CommonResult blockHandle(@PathVariable Long id,BlockException blockException){
        Payment payment = new Payment(id, null);
        return new CommonResult(445,"BlockHandle-sentinel限流，blockException  "+blockException.getMessage(),payment);
    }
    @Resource
    private PaymentService paymentService;
    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id")Long id){
        return paymentService.paymentSQL(id);
    }

}
