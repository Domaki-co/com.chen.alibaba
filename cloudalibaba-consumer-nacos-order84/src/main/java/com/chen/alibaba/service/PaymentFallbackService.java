package com.chen.alibaba.service;

import com.chen.springcloud.entity.CommonResult;
import com.chen.springcloud.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444,"服务降级返回____PaymentFallbackService",new Payment(4444L,"errorserial4444"));
    }
}
