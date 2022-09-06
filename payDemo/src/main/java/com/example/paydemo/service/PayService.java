package com.example.paydemo.service;

import com.example.paydemo.entity.OrderInfo;
import com.icbc.api.IcbcApiException;
import com.icbc.api.response.CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1;

public interface PayService {

    CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 executZfb(OrderInfo orderInfo) throws IcbcApiException;

    CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 executWx(OrderInfo orderInfo) throws IcbcApiException;
}
