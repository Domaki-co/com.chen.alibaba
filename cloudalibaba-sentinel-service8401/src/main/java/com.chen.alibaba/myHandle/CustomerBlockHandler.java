package com.chen.alibaba.myHandle;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.springcloud.entity.CommonResult;
import com.chen.springcloud.entity.Payment;

public class CustomerBlockHandler {
    public static CommonResult handleException(BlockException exception){
        return new CommonResult(4444,"按用户自定义1");
    }
    public static CommonResult handleException2(BlockException exception){
        return new CommonResult(5555,"按用户自定义2");
    }
}
