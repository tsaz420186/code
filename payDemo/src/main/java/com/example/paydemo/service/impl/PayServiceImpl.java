package com.example.paydemo.service.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import cn.hutool.core.date.DateUtil;
import com.example.paydemo.entity.DetailWxInfo;
import com.example.paydemo.entity.DetailZfbInfo;
import com.example.paydemo.entity.OrderInfo;
import com.example.paydemo.service.PayService;
import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcApiException;
import com.icbc.api.IcbcConstants;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.api.request.CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1;
import com.icbc.api.request.CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1.CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1Biz;
import com.icbc.api.response.CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {

    // 1、APP编号（工行API平台生成）（测试环境参数，生产请修改）（测试参数表内容1）
    private static String APP_ID = "10000000000000225585";
    // 2、网关公钥（测试环境参数，生产请修改）
    private static final String APIGW_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwFgHD4kzEVPdOj03ctKM7KV+16bWZ5BMNgvEeuEQwfQYkRVwI9HFOGkwNTMn5hiJXHnlXYCX+zp5r6R52MY0O7BsTCLT7aHaxsANsvI9ABGx3OaTVlPB59M6GPbJh0uXvio0m1r/lTW3Z60RU6Q3oid/rNhP3CiNgg0W6O3AGqwIDAQAB";

    //3、合作方私钥（公钥在工行API平台登记）（测试环境参数，生产环境请修改）
    protected static String MY_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCGImy94nDdhrZlSDxDnJbIPoK12q17auxP8yR/i/qKeus15r72d84AL2fpPNnIFHpCJ+5Rv+2cKsqbd/rZSp5XGk3I6XTvjJGNK3uoGlLX8pYNzkd/jXRPG2gYocLxOwLQVjQla7O3qJkMpSCsASlkbN7eE0rlzyMeCYX3Ztxk9FxMaixLWyDrtQre5sOjWFnkWQFT3MH4LrnelqFCOH9AP76bkKgUPV89O2C9aq1QC71iHdrvz8fGpDpPLt0k8zlBHrSRATdeqRO8LiwtCnRo51zTfyQszaRyUbSwuRMvcImI5vQZ/WuLdWIGvoIbmSV8f4FuKQbFbu73acgFbXRlAgMBAAECggEAWHVTaVZwQGea2ehtddlKxvwSj4OLbRzygzdCFqtlqvWtMHrf7QLNY+nfiT2MEhsPFZnn3RnJxXei7iyNzccb7S13psvvRhtZ0dOfVRknESr2MlybYk8TKShV2giz2wqjrGltMlB40OHrLR+pEEYj2grCX4oxlqSn8CMvqhy3tAtOdvUp2jOliQQR5VlYX4xvatR3krfCkZnBv8jyXLaQYboKLUh372seZYffYqvTrtSRYbtwaj8l6O4EN+CP4zbCUHyjwpMlYz4y1PTSp6C0edAzDI6KYJOowXSDYINgzXKVc2UJAVjE6SYXawACMUdARRajTOe/3L3hyTKqrVz9cQKBgQC690u+4qR+yBmHONV3puRpqjMtrBYsgq6yP/+4dJqGCHWM4294CkhAyt0YqRlOXTtt/vbVqsZiPAIWvAn/vytzsjfoYlsVWN9iJO4r0caFgTV8s7ybP/WaLZb5ikM/TATFMAQnO/7J8mQLaAnfo3+9GOLmmVgTqlkvXEHIUVO6YwKBgQC3qU0lCA/WiyKd3j9edhAac7K2ltbgdk3B9CbUfRqLHbal9bcPiHt4PQXClFWZEyENvqwvSmTTc6DOLMlpiptcyxxprCfl8oczGa446IyG8zqsFyBXORlIMda7HtatAPIcfwQ6dKVwPA5Tdlw64HsbgRF6kIQDyFbvlt18ZfCslwKBgQCB/3CrSOsXPAqD+wByMZif37e2CfFhAXx2dAlaN7k5eKhDTeAzlvKmGmegvfLaifjIgn8eEZ/erdrF8/j/2exchY5aBglOznyDwSnZTgK60RxfmJmLWO1sRkazO2+Gk1S9xqUcopqF0AO/6pby6Lvov2UE8ZpMEiXwyDT1E5ckAQKBgCgPdrNIIeRDLwbOTnY1PGf4qpenKTGGvIk8zyd0LlD/5zIl+Ysa0wu0C8iYgF9GS+RqnbYm/P0ecInoPPqXcDH6X7UxBvbiQtlDlbv2BYj+DuMwnC5KVxhnPpZMMLlFxomuruZtQRTNFLbhz1ZMVq32JjDcyt7kWQojlyYmOiv7AoGBAKLDFwAOkMH/XjAdj00QZwV9ranlkKddzMKBcA/JebbFMsQQhasOxS0uIGoDkA14LDDKNOa2YqaMFyiRu/nQEpnrFIJFjMSsLGqArfPxext8XIrznzGPhmagGdLEF66+G7m9wmf9x23TwUTCcBRY1iwRzjARVEU6X9eT17C2jPVz";

    private Logger logger = Logger.getLogger(PayServiceImpl.class.getName());

    public CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 execute(OrderInfo orderInfo, Map<String,String> params) throws IcbcApiException {

        //4、签名类型为RSA时，需传入appid，私钥和网关公钥，签名类型使用定值IcbcConstants.SIGN_TYPE_RSA，其他参数使用缺省值
        DefaultIcbcClient client = new DefaultIcbcClient(APP_ID, IcbcConstants.SIGN_TYPE_RSA,MY_PRIVATE_KEY, APIGW_PUBLIC_KEY);

        CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1 request = new CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1();
        //5、根据测试环境和生产环境替换相应ip和端口
        request.setServiceUrl("http://gw-sandbox.open.icbc.com.cn/api/cardbusiness/aggregatepay/b2c/online/consumepurchase/V1");

        CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1Biz bizContent = new CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1Biz();
        request.setBizContent(bizContent);

        //6、请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
        //商户编号
        bizContent.setMer_id(orderInfo.getMerId());
        //商户订单号，只能是数字、大小写字母，且在同一个商户号下唯一
        bizContent.setOut_trade_no(orderInfo.getOutTradeNo());
        //支付方式，9-微信；10-支付宝；13-云闪付
        bizContent.setPay_mode(params.get("payMode"));
        //收单接入方式，5-APP，7-微信公众号，8-支付宝生活号，9-微信小程序
        bizContent.setAccess_type(params.get("accessType"));
        //收单产品协议编号
        bizContent.setMer_prtcl_no(orderInfo.getMerPrtclNo());
        //交易日期时间，格式为yyyy-MM-dd’T’HH:mm:ss
        bizContent.setOrig_date_time(DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss"));
        //设备号
        bizContent.setDecive_info(orderInfo.getDeciveInfo());
        /**
         * 商品描述，商品描述交易字段格式根据不同的应用场景按照以下格式：
         * 1）PC网站：传入浏览器打开的网站主页title名-实际商品名称 ；
         * 2）公众号：传入公众号名称-实际商品名称；
         * 3）H5：传入浏览器打开的移动网页的主页title名-实际商品名称；
         * 4）线下门店：门店品牌名-城市分店名-实际商品名称；
         * 5）APP：传入应用市场上的APP名字-实际商品名称
         */
        bizContent.setBody(orderInfo.getBody());
        //交易币种，目前工行只支持使用人民币（001）支付
        bizContent.setFee_type("001");
        //用户端IP
        bizContent.setSpbill_create_ip(orderInfo.getSpbillCreateIp());
        //订单金额，单位为分
        bizContent.setTotal_fee(orderInfo.getTotalFee());
        //异步通知商户URL，端口必须为443或80
        bizContent.setMer_url(orderInfo.getMerUrl());
        //商户在微信开放平台注册的APPID，支付方式为微信时不能为空
        if("9".equals(bizContent.getPay_mode())){
            bizContent.setShop_appid(params.get("shopAppid"));
        }
        //商户在工行API平台的APPID
        bizContent.setIcbc_appid(APP_ID);

        switch (bizContent.getAccess_type()){
            case "7":
            case "9":
                //第三方用户标识，商户在微信公众号内或微信小程序内接入时必送，即access_type为7或9时，上送用户在商户APPID下的唯一标识；商户通过支付宝生活号接入时不送
                bizContent.setOpen_id(orderInfo.getOpenId());
                break;
            case "8":
                //第三方用户标识，商户在支付宝生活号接入时必送，即access_type为8时，上送用户的唯一标识；商户通过微信公众号内或微信小程序接入时不送
                bizContent.setUnion_id(orderInfo.getUnionId());
        }

        //商户账号，商户入账账号，只能交易时指定。（商户付给银行手续费的账户，可以在开户的时候指定，也可以用交易指定方式；用交易指定方式则使用此商户账号）目前暂不支持
        bizContent.setMer_acct(orderInfo.getMerAcct());
        //订单失效时间，单位为秒，建议大于60秒
        bizContent.setExpire_time("120");
        /**
         *         通知类型，表示在交易处理完成后把交易结果通知商户的处理模式。
         *         取值“HS”：在交易完成后将通知信息，主动发送给商户，发送地址为mer_url指定地址;
         *         取值“AG”：在交易完成后不通知商户;
         */
        bizContent.setNotify_type("AG");
        /**
         *         结果发送类型，通知方式为HS时有效。
         *         取值“0”：无论支付成功或者失败，银行都向商户发送交易通知信息；
         *         取值“1”，银行只向商户发送交易成功的通知信息。默认是”0”
         */
        bizContent.setResult_type("0");
        /**
         *         支付方式限定，
         *         上送”no_credit“表示不支持信用卡支付；
         *         上送“no_balance”表示仅支持银行卡支付；
         *         不上送或上送空表示无限制
         */
        bizContent.setPay_limit("no_credit");
        //订单附加信息
        bizContent.setOrder_apd_inf("xxx");
        //商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传。微信与支付宝的规范不同，请根据支付方式对应相应的规范上送
        bizContent.setDetail(params.get("detailInfo"));
        //支付成功回显页面，支付成功后，跳转至该页面显示。当access_type=5且pay_mode=10才有效
        if("5".equals(bizContent.getAccess_type()) && "10".equals(bizContent.getPay_mode())){
            bizContent.setReturn_url("xxx");
        }

        //用户付款中途退出返回商户网站的地址（仅对浏览器内支付时有效）当access_type=5且pay_mode=10才有效
        if("5".equals(bizContent.getAccess_type()) && "10".equals(bizContent.getPay_mode())){
            bizContent.setQuit_url("xxx");
        }

//        bizContent.setCust_name("xxx");
//        bizContent.setCust_cert_type("xxx");
//        bizContent.setCust_cert_no("xxx");
        CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 response = new CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1();
        try {

            response = client.execute(request, System.currentTimeMillis()+"");//msgId消息通讯唯一编号，要求每次调用独立生成，APP级唯一
            if (response.getReturnCode() == 0) {
                // 6、业务成功处理，请根据接口文档用response.getxxx()获取同步返回的业务数据
                logger.info("ReturnCode:"+response.getReturnCode());
                logger.info("response:" + JSON.toJSONString(response));
            } else {
                // 失败
                logger.info("ReturnCode:"+response.getReturnCode());
                logger.info("response:" + JSON.toJSONString(response));
                logger.info("ReturnMsg:"+response.getReturnMsg());
            }
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 executZfb(OrderInfo orderInfo) throws IcbcApiException {

        Map<String,String> params = new HashMap<>();
        params.put("payMode","10");
        params.put("accessType","8");

        DetailZfbInfo detailZfbInfo = new DetailZfbInfo();
        params.put("detailInfo", JSON.toJSONString(detailZfbInfo));
        return execute(orderInfo,params);
    }

    @Override
    public CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 executWx(OrderInfo orderInfo) throws IcbcApiException {

        Map<String,String> params = new HashMap<>();
        params.put("payMode","9");
        params.put("accessType","9");
        params.put("shopAppid",orderInfo.getShopAppid());
        DetailWxInfo detailWxInfo = new DetailWxInfo();
        params.put("detailInfo", JSON.toJSONString(detailWxInfo));
        return execute(orderInfo,params);
    }
}
