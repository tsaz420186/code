package com.example.paydemo.controller;

import com.example.paydemo.entity.OrderInfo;
import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcApiException;
import com.icbc.api.IcbcConstants;
import com.icbc.api.IcbcResponse;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.api.request.CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1;
import com.icbc.api.response.CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@RequestMapping("alipay")
public class AlipayController {

    @GetMapping("order")
    public String order(OrderInfo orderInfo){

        CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 response = order();
        return response.getReturnMsg();
    }


    public CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 order(){

        // 1、APP编号（工行API平台生成）（测试环境参数，生产请修改）（测试参数表内容1）
        String APP_ID = "Oikeclo001";
        // 2、网关公钥（测试环境参数，生产请修改）
        String APIGW_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwFgHD4kzEVPdOj03ctKM7KV+16bWZ5BMNgvEeuEQwfQYkRVwI9HFOGkwNTMn5hiJXHnlXYCX+zp5r6R52MY0O7BsTCLT7aHaxsANsvI9ABGx3OaTVlPB59M6GPbJh0uXvio0m1r/lTW3Z60RU6Q3oid/rNhP3CiNgg0W6O3AGqwIDAQAB";

        String MY_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCGrZiFbuBpFtHoZUWPs2pQEsbpjd56aw45JYGTfRxxC31ms0VqVs09acjSNsZeFsehTm1ZGJAmP+gxGhhS/gxMKJjbvn0tfzGW+42yjBMG1otL888XV4tXjzAkIuatHxqfhlnN2Ib8ZIlawcF7b/nGB0sk9dzBF7oGFcernuWE8olZJAA/7HSc6Ai1cU1KEr8kTrot368LoHGTaL5dApvHe6p7nsMUzFO7T+7yNqbTlnUQI4ju9x271F3/D4EeqNJ/EPciYoVfzwvd23YbTai/j/KHKAXGz+dkdQ2k9ml6S4ji3DUQxdflQTjYCkHwEZaX1awcscBpvJ+Nc/qzmHeJAgMBAAECggEAfqrxQ7Zn3I8499GgHirJ9+vhJ7NmJ09fPkJXX6eRQ4Vh+WDtDbn8KSHAXm5kHgRg2AcSc3TVxxPeR6hablxAprScKDk+NLdnpbCMsfxqvMi2lMYH5dVR2hVzWtl0iikx+g37ks5vDx1jzndjibxkeGtNssKHURkGvkhn1rOI/YNeGFkVN5t7JcMShLIF19/uU28fR4LfneWEe7mQ1evt2/CvwPIjVSMtp3vgLr20vJ7tiuIz25vIdHxCLxvDShMK7W5gsGa8rtE061FXqIDDb/pUp5uzSOUfhJQbrlW48o3FohJJTu5oF8izyEltwPp7PMqQV7wB3/F5NNYxibQ5sQKBgQDC5OOk7Y45yhBhwe1IYf5mJfASb0qwEdax131PcgtPe/Dyaei+t2808iuG5RhusFe4l2Bs1t7fJgG5Q4c8M20ajyttApz0rts+z+Xh5fSMGfj4iQzbuavMvEij9XzNVpX7y9eLy3SbpCGOEjoNSfFJdA0mxwYMo7L4KlchYmD/ZQKBgQCw531qOYqaju+5F+un/+QLf03vg3bq+p0wAtTZlCPPFrLAzP8busNl75LohBMrdOC9b6FafihEbo4sylOGoKgiTqjF+y0N/cy7mKzyl0EU5vQ53XobcSckf0QQdtky+pwx6kBEPi/8EM9pg2cLzOU80Qbzj0p8RB2LALeby83PVQKBgQCv1cJs8sb27I+LI2Ppgj/7+Q54E2DXKcECAuFT5BjARZpFm7gZGXaD1lnfAqLgeskqCak9iT7bJXc4puxELEGrTLBpUYo6V+gG65sEDGxuWqB922h2dTXDKbx/8OQAfrYizu5ReeZi5dpAS8GEa8n9825KJhYXITo/HDQwMopA4QKBgFy+H7vuysFYqkjJR5xdxPpaFUu6CkAKfxnKgjhHN7a72Z7GWf/5m7L3NHd9tmcT8TafLWI5vXaS1yqYL83mr/68ajLRCb/KDOlUk2bi8RW6NtOXTwrP+YtixRY0mI3nYLeybVrZl2J4laRlZUKEerG1jAVoW85mD27uQ1QENrO5AoGBAL0hZJKrCtagFlLPuHP2vbdvH1kFTwYpkpMPZSG6ZodXHmfWswv0ezJtjqeHVM9JxlsVT/ZOiDQigdVExsznjycn972AwfooppJYFwJc4ewWX7zqDupxrXXKFpV7aHluYwDQsXA3EMNJdhA4kxjFC9rAJi8ZXk+G233b+BLozv38";

        DefaultIcbcClient client = new DefaultIcbcClient(APP_ID, IcbcConstants.SIGN_TYPE_RSA2, MY_PRIVATE_KEY, APIGW_PUBLIC_KEY);

        CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1 request = new CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1();

        // 生产请求路径：https://gw.open.icbc.com.cn/api/+接口服务地址
        // 沙箱测试请求路径：https://gw-sandbox.open.icbc.com.cn/api/+接口服务地址
        request.setServiceUrl("https://gw-sandbox.open.icbc.com.cn/api/cardbusiness/aggregatepay/b2c/online/consumepurchase/V1");

        CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1.CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1Biz bizContent = new CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1.CardbusinessAggregatepayB2cOnlineConsumepurchaseRequestV1Biz();
        request.setBizContent(bizContent);

        bizContent.setMer_id("020001021189");//商户编号
        bizContent.setOut_trade_no("65964126858");//商户订单号，只能是数字、大小写字母，且在同一个商户号下唯一
        bizContent.setPay_mode("9");//支付方式，9-微信；10-支付宝；13-云闪付

        bizContent.setAccess_type("5");//收单接0入方式，5-APP，7-微信公众号，8-支付宝生活号，9-微信小程序
        bizContent.setMer_prtcl_no("0200509414830201");//收单产品协议编号
        bizContent.setOrig_date_time("2022-04-20T08:45:17");//交易日期时间，格式为yyyy-MM-dd’T’HH:mm:ss
        bizContent.setDecive_info("013467007045764");//设备号
        bizContent.setBody("测试");//商品描述，商品描述交易字段格式根据不同的应用场景按照以下格式：1）PC网站：传入浏览器打开的网站主页title名-实际商品名称 ；2）公众号：传入公众号名称-实际商品名称；3）H5：传入浏览器打开的移动网页的主页title名-实际商品名称；4）线下门店：门店品牌名-城市分店名-实际商品名称；5）APP：传入应用市场上的APP名字-实际商品名称
        bizContent.setFee_type("001");//交易币种，目前工行只支持使用人民币（001）支付
        bizContent.setSpbill_create_ip("122.12.12.12");//用户端IP
        bizContent.setTotal_fee("100");//订单金额，单位为分
        bizContent.setMer_url("http:\\/\\/www.test.com\\/notifyurl");//异步通知商户URL，端口必须为443或80
//        bizContent.setShop_appid("wx3bf6ce2f02e0f2c2");//商户在微信开放平台注册的APPID，支付方式为微信时不能为空
//        bizContent.setShop_appid("wxb2f780e60a837d91");//商户在微信开放平台注册的APPID，支付方式为微信时不能为空
//        bizContent.setShop_appid("1");//商户在微信开放平台注册的APPID，支付方式为微信时不能为空
        bizContent.setIcbc_appid("10000000000000002889");//商户在工行API平台的APPID
//        bizContent.setOpen_id("ojdq81XzrVb5GNp4mll93_ugwqY0");
//        bizContent.setUnion_id("2088731984846534");
        //bizContent.setMer_acct("6212880200000038618");
        //bizContent.setExpire_time("120");
        //bizContent.setAttach("{ \"id\": \"SZTX001\", \"name\": \"腾大餐厅\", \"area_code\": \"440305\", \"address\": \"科技园中一路腾讯大厦\" }");
        bizContent.setNotify_type("AG");//通知类型，表示在交易处理完成后把交易结果通知商户的处理模式。 取值“HS”：在交易完成后将通知信息，主动发送给商户，发送地址为mer_url指定地址； 取值“AG”：在交易完成后不通知商户
        //bizContent.setResult_type("0");
        bizContent.setPay_limit("no_credit");
        //bizContent.setOrder_apd_inf("");
        CardbusinessAggregatepayB2cOnlineConsumepurchaseResponseV1 response = null;
        try {
            response = client.execute(request,System.currentTimeMillis()+"");//msgId消息通讯唯一编号，要求每次调用独立生成，APP级唯一
            if (response.getReturnCode() == 0) {
                // 6、业务成功处理，请根据接口文档用response.getxxx()获取同步返回的业务数据
                System.out.println("ReturnCode:"+response.getReturnCode());
                System.out.println("response:" + JSON.toJSONString(response));
            } else {
                // 失败
                System.out.println("response:" + JSON.toJSONString(response));
                System.out.println("ReturnCode:"+response.getReturnCode());
                System.out.println("ReturnMsg:"+response.getReturnMsg());
            }
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }


        return response;
    }






}
