package chiya.web.wechat.entity;

/**
 * 微信的API
 * 
 * @author chiya
 */
public class WechatAPI {

	/**
	 * 微信小程序登录用的获取OPENID接口
	 */
	public static final String JS_CODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session";

	/**
	 * V3的JSAPI下单接口
	 */
	public static final String JSAPI = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

	/**
	 * Native支付接口
	 */
	public static final String NATIVE = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";

	/**
	 * 根据商户的订单号查询订单状态接口
	 */
	public static final String OUT_TRADE_NO = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/";

	/**
	 * V3退款接口
	 */
	public static final String REFUND = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

	/**
	 * 微信消息订阅
	 */
	public static final String SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

	/**
	 * 微信获取服务端Token
	 */
	public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

	/**
	 * 获取手机号
	 */
	public static final String GET_PHONE = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";
}
