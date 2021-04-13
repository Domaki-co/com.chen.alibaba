package com.chen.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.alibaba.myHandle.CustomerBlockHandler;
import com.chen.springcloud.entity.CommonResult;
import com.chen.springcloud.entity.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/bySource")
    @SentinelResource(value = "bySource",blockHandler = "handleException")
    public CommonResult bySource(){
        return new CommonResult(200,"按资源名称限流测试成功",new Payment(2021L,"serial2021"));
    }
    public CommonResult handleException(BlockException exception){
        return new CommonResult(444,exception.getClass().getCanonicalName()+"\t 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult url(){
        return new CommonResult(200,"按URL限流测试成功",new Payment(20211L,"serial0002"));
    }


    //CustomerBlockHandler
    @GetMapping("/rateLimit/CustomerBlockHandler")
    @SentinelResource(value = "CustomerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handleException2")
    public CommonResult CustomerBlockHandler(){
        return new CommonResult(200,"按用户自定义");
    }
}
