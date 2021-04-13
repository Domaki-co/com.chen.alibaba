package com.chen.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        log.info(Thread.currentThread().getName()+"\t"+"____testA");
        return "---testA---";
    }

    @GetMapping("/testB")
    public String testB(){
    log.info(Thread.currentThread().getName()+"\t"+"____testB");
        return "---testB---";
    }
    @GetMapping("/testD")
    public String testD(){
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "---testD---";
    }
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey") //blockHandler 兜底的方法，不加会给用户报异常
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2){
        return "____hotKey___";
    }

    public String deal_testHotKey(String p1, String p2, BlockException blockException){
        return "deal_testHotKey -> 兜底方法调用";
    }
}
