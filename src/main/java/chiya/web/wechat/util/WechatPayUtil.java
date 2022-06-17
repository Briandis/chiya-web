package chiya.web.wechat.util;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;

import chiya.core.base.exception.Assert;
import chiya.core.base.string.StringUtil;
import chiya.core.base.time.DateUtil;
import chiya.web.request.http.HttpMethod;
import chiya.web.request.http.RequestUtil;
import chiya.web.wechat.config.WeChatConfig;
import chiya.web.wechat.entity.Refunds;
import chiya.web.wechat.entity.Resource;
import chiya.web.wechat.entity.WechatAPI;
import chiya.web.wechat.entity.WechatCallbackResouce;

/**
 * 微信支付结果回调对象工具库
 * 
 * @author chiya
 */
public class WechatPayUtil {

	/** 私钥对象 */
	private static PrivateKey PRIVATE_KEY = null;

	/** 签名 */
	private static PrivateKeySigner PRIVATE_KEY_SIGNER = null;

	/**
	 * 构造配置并初始化
	 */
	public static void init() {
		PRIVATE_KEY = PemUtil.loadPrivateKey(new ByteArrayInputStream(WeChatConfig.PRIVATE_KEY.getBytes()));
		PRIVATE_KEY_SIGNER = new PrivateKeySigner(WeChatConfig.ERCHANT_SERIAL_NUMBER, PRIVATE_KEY);
	}

	/**
	 * 加密数据解码反序列化
	 * 
	 * @param resource 微信支付回调通知数据对象
	 * @return 解密后的对象
	 */
	public static WechatCallbackResouce resouceEncode(Resource resource) {
		if (resource == null) { return null; }
		AesUtil aesUtil = new AesUtil(WeChatConfig.API_V3_KEY.getBytes());
		try {
			String jsonDate = aesUtil.decryptToString(
				resource.getAssociated_data().getBytes(),
				resource.getNonce().getBytes(),
				resource.getCiphertext()
			);
			return JSON.parseObject(jsonDate, WechatCallbackResouce.class);
		} catch (Exception e) {
			Assert.fail("微信加密回调解码失败");
		}
		return null;
	}

	/**
	 * 创建支付链接请求
	 * 
	 * @return HttpClient
	 */
	public static HttpClient request() {
		// 自动获取平台证书
		AutoUpdateCertificatesVerifier verifier = createAutoUpdateCertificatesVerifier();
		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
			// merchantId商户号，erchantSerialNumber商户API证书的证书序列号，merchantPrivateKey商户API私钥
			.withMerchant(WeChatConfig.MCHID, WeChatConfig.ERCHANT_SERIAL_NUMBER, PRIVATE_KEY)
			// wechatpayCertificates微信支付平台证书
			.withValidator(new WechatPay2Validator(verifier));
		return builder.build();
	}

	/**
	 * 创建自动获取平台证书对象
	 * 
	 * @return AutoUpdateCertificatesVerifier 自动获取的对象
	 */
	public static AutoUpdateCertificatesVerifier createAutoUpdateCertificatesVerifier() {
		return new AutoUpdateCertificatesVerifier(new WechatPay2Credentials(WeChatConfig.MCHID, PRIVATE_KEY_SIGNER), WeChatConfig.API_V3_KEY.getBytes());
	}

	/**
	 * 获取私钥对象
	 * 
	 * @return PrivateKey 私钥对象
	 */
	public static PrivateKey getPrivatekey() {
		return PRIVATE_KEY;
	}

	/**
	 * 封装微信支付预订单的请求体
	 * 
	 * @param orderId     订单id
	 * @param productInfo 商品描述信息
	 * @param total       支付金额（分为单位）
	 * @param openid      微信的用户id
	 * @return 封装后的JSON字符串
	 */
	public static String createPayOrderBody(String orderId, String productInfo, Long total, String openid) {
		JSONObject jsonObject = new JSONObject();
		// 商家id
		jsonObject.put("mchid", WeChatConfig.MCHID);
		// 小程序id
		jsonObject.put("appid", WeChatConfig.APPID);
		// 订单号
		jsonObject.put("out_trade_no", orderId);
		// 商品描述
		jsonObject.put("description", productInfo);
		// 回调地址
		jsonObject.put("notify_url", WeChatConfig.CALLBACK_PATH);
		// 当前时间+10分钟，作为支付失效时间
		long l = System.currentTimeMillis() + 1000 * 60 * 10;
		// 转成UTC标准时间
		jsonObject.put("time_expire", DateUtil.formatDateUTCDateTime(l));
		JSONObject amount = new JSONObject();
		// 订单金额，向下取整
		amount.put("total", total);
		jsonObject.put("amount", amount);
		// 谁支付的，用户的openid
		JSONObject payer = new JSONObject();
		payer.put("openid", openid);
		jsonObject.put("payer", payer);
		return jsonObject.toJSONString();
	}

	/**
	 * 封装微信支付退款请求体
	 * 
	 * @param orderId     订单id
	 * @param outRefundNo 商户退款单号
	 * @param total       退款金额（分为单位）
	 * @param reason      退款原因
	 * @return 封装后的JSON字符串
	 */
	public static String createRefundOrderBody(String orderId, String outRefundNo, Long total, String reason) {
		JSONObject jsonObject = new JSONObject();
		// 商户订单号
		jsonObject.put("out_trade_no", orderId);
		// 商户退款单号
		jsonObject.put("out_refund_no", outRefundNo);

		// 回调地址
		jsonObject.put("notify_url", WeChatConfig.REFUNDS_CALLBACK_PATH);
		JSONObject amount = new JSONObject();
		// 原订单金额
		amount.put("total", total);
		// 退款金额-两者相等一次性退完
		amount.put("refund", total);
		// 退款货币类型
		amount.put("currency", "CNY");
		jsonObject.put("amount", amount);
		return jsonObject.toJSONString();
	}

	/**
	 * 调用微信支付对象
	 * 
	 * @param orderId     订单号
	 * @param productInfo 商品猫叔
	 * @param total       支付金额（分）
	 * @param openid      微信id
	 * @return prepay_id 支付id
	 */
	public static String sendPayOrder(String orderId, String productInfo, Long total, String openid) {
		// HTTP链接客户端
		HttpClient httpClient = WechatPayUtil.request();
		// 创建订单信息的请求体
		String body = WechatPayUtil.createPayOrderBody(orderId, productInfo, total, openid);
		// JSAPI支付路径
		HttpPost httpPost = HttpMethod.postJson(WechatAPI.JSAPI, body);
		String bodyAsString = RequestUtil.httpRquest(httpClient, httpPost);

		String PREPAY_ID = "prepay_id";
		JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
		if (jsonObject != null && jsonObject.containsKey(PREPAY_ID)) {
			// 获取prepay_id并返回
			return jsonObject.get(PREPAY_ID).toString();
		}
		Assert.fail("未获取到微信支付的prepay_id");
		return null;
	}

	/**
	 * 获取订单状态
	 * 
	 * @param orderId 商户的订单号
	 * @return WechatCallbackResouce 支付订单的返回对象
	 */
	public static WechatCallbackResouce sendCheckOrderPay(String orderId) {
		HttpClient httpClient = WechatPayUtil.request();
		// 订单路径+订单号+商户参数，详情见微信支付官方文档
		String url = WechatAPI.OUT_TRADE_NO + orderId + "?mchid=" + WeChatConfig.MCHID;
		String bodyAsString = RequestUtil.httpRquest(
			httpClient,
			HttpMethod.getJson(url),
			() -> Assert.fail("获取订单状态时异常")
		);
		return JSON.parseObject(bodyAsString, WechatCallbackResouce.class);
	}

	/**
	 * 微信支付退款
	 * 
	 * @param orderId     订单id
	 * @param outRefundNo 交易单号
	 * @param total       退款金额（分为单位）
	 * @param reason      退款原因
	 * @return 成功/失败
	 */
	public static boolean sendRefundsOrder(String orderId, String outRefundNo, Long total, String reason) {
		HttpClient httpClient = WechatPayUtil.request();
		// 创建退款请求体
		String body = WechatPayUtil.createRefundOrderBody(orderId, outRefundNo, total, reason);
		String bodyAsString = RequestUtil.httpRquest(
			httpClient,
			HttpMethod.postJson(WechatAPI.REFUND, body),
			() -> Assert.fail("微信支付退款异常")
		);
		JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
		if (jsonObject != null) {
			// 请获取退款状态
			String status = jsonObject.get("status").toString();
			return StringUtil.eqString("SUCCESS", status) || StringUtil.eqString("PROCESSING", status);
		}
		return false;
	}

	/**
	 * 关闭交易单号
	 * 
	 * @param orderId 商户订单号
	 * @return 关闭成功/失败
	 */
	public static boolean sendColseOrder(String orderId) {
		HttpClient httpClient = WechatPayUtil.request();
		// 创建POST退款接口对象
		String url = WechatAPI.OUT_TRADE_NO + orderId + "/close";
		HttpPost httpPost = HttpMethod.postJson(url);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mchid", WeChatConfig.MCHID);
		String body = jsonObject.toJSONString();
		try {
			httpPost.setEntity(new StringEntity(body));
			HttpResponse response = httpClient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			// 200或204下才算成功
			return code == 200 || code == 204;
		} catch (Exception e) {
			Assert.fail("关闭交易单号失败");
		}
		return false;
	}

	/**
	 * 查询退款状态
	 * 
	 * @param orderId 商户订单号
	 */
	public static Refunds sendCheckRefundsOrder(String orderId) {
		HttpClient httpClient = WechatPayUtil.request();
		// 退款路径URL+/商户订单号
		String url = WechatAPI.REFUND + "/" + orderId;
		String bodyAsString = RequestUtil.httpRquest(
			httpClient,
			HttpMethod.getJson(url),
			() -> Assert.fail("退款查询出现异常")
		);
		return JSON.parseObject(bodyAsString, Refunds.class);
	}

	/**
	 * 调用微信支付对象
	 * 
	 * @param orderId     订单号
	 * @param productInfo 商品描述
	 * @param total       支付金额（分）
	 * @param openid      微信id
	 * @return prepay_id 支付id
	 */
	public static String sendPayOrderToNative(String orderId, String productInfo, Long total, String openid) {
		// HTTP链接客户端
		HttpClient httpClient = WechatPayUtil.request();
		// 创建订单信息的请求体
		String body = WechatPayUtil.createPayOrderBody(orderId, productInfo, total, openid);
		// JSAPI支付路径
		HttpPost httpPost = HttpMethod.postJson(WechatAPI.NATIVE, body);
		String bodyAsString = RequestUtil.httpRquest(httpClient, httpPost);
		// 二维码的字段
		String PREPAY_ID = "code_url";
		JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
		if (jsonObject != null && jsonObject.containsKey(PREPAY_ID)) {
			// 获取prepay_id并返回
			return jsonObject.get(PREPAY_ID).toString();
		}
		Assert.fail("未获取到微信支付的code_url");
		return null;
	}
}
