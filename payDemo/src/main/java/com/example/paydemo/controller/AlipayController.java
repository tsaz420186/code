package com.example.paydemo.controller;

import com.example.paydemo.entity.OrderInfo;
import com.example.paydemo.service.PayService;
import com.example.paydemo.service.impl.PayServiceImpl;
import com.icbc.api.IcbcApiException;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.api.response.CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("pay")
public class AlipayController {

    @Autowired
    private PayService payService;

    /**
     * 支付宝下单
     * @param orderInfo
     * @return
     */
    @PostMapping("aliypay")
    public String order(@RequestBody OrderInfo orderInfo){

        try {
            CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 response = payService.executZfb(orderInfo);

            return JSON.toJSONString(response);
        } catch (IcbcApiException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 微信下单
     * @param orderInfo
     * @return
     */
    @PostMapping("wxpay")
    public String order2(@RequestBody OrderInfo orderInfo){

        try {
            CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 response = payService.executWx(orderInfo);
            return JSON.toJSONString(response);
        } catch (IcbcApiException e) {
            throw new RuntimeException(e);
        }

    }




}
