package com.example.paydemo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetailWxInfo implements Serializable {

    /**
     * 订单原价：
     * 1.商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的交易金额。
     * 2.当订单原价与支付金额不相等，则不享受优惠。
     * 3.该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传此参数。
     * PS：单品券优惠cost_price需上送，且与biz_content内的total_fee保持一致，否则无法享受优惠
     */
   private Integer  costPrice;
   //商家小票ID
   private String  receiptId;

    /**
     * 单品信息，使用Json数组格式提交。
     * 注意goods_detail字段的格式为”goods_detail”:[{}],较多商户写成”goods_detail”:{}
     */
    private GoodsDetail goodsDetail;
}
