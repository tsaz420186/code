package com.example.paydemo.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderInfo implements Serializable {

    /**
     * 商户编号
     */
    private String merId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 收单产品协议编号
     */
    private String merPrtclNo;
    /**
     * 设备号
     */
    private String deciveInfo;


    /**
     * 商品描述，商品描述交易字段格式根据不同的应用场景按照以下格式：
     * 1）PC网站：传入浏览器打开的网站主页title名-实际商品名称 ；
     * 2）公众号：传入公众号名称-实际商品名称；
     * 3）H5：传入浏览器打开的移动网页的主页title名-实际商品名称；
     * 4）线下门店：门店品牌名-城市分店名-实际商品名称；
     * 5）APP：传入应用市场上的APP名字-实际商品名称
     */
    private String body;

    /**
     * 用户端ip
     */
    private String spbillCreateIp;

    /**
     * 订单金额，单位为分
     */
   private String totalFee;

    /**
     * 异步通知商户URL，端口必须为443或80
     */
    private String merUrl;

    /**
     * 商户在微信开放平台注册的APPID，支付方式为微信时不能为空
     */
    private String shopAppid;

    /**
     *   第三方用户标识，商户在微信公众号内或微信小程序内接入时必送，即access_type为7或9时，上送用户在商户APPID下的唯一标识；商户通过支付宝生活号接入时不送
     */
    private String openId;

    /**
     * 第三方用户标识，商户在支付宝生活号接入时必送，即access_type为8时，上送用户的唯一标识；商户通过微信公众号内或微信小程序接入时不送
     */
    private String unionId;

    /**
     *  商户账号，商户入账账号，只能交易时指定。
     */
    private String merAcct;

    /**
     * 订单附加信息
     */
    private String orderApdInf;

}
