package chiya.web.wechat.entity;

import java.security.Signature;
import java.util.Base64;

import com.alibaba.fastjson.JSON;

import chiya.core.base.random.RandomString;
import chiya.web.wechat.config.WeChatConfig;
import chiya.web.wechat.util.WechatPayUtil;

/**
 * 支付单返回的信息，给前台调用支付接口使用
 * 
 * @author brian
 *
 */
public class PayResult {

	/**
	 * 时间戳，不包含毫秒的时间戳
	 */
	private String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
	/**
	 * 随机字符串，最大长度32
	 */
	private String nonceStr = RandomString.randomStringByUUID();

	/**
	 * 订单详情扩展字符串，需要接收预订单的返回数据
	 */
	private String packages;
	/**
	 * 签名方式，RSA默认值
	 */
	private String signType = "RSA";
	/**
	 * 签名，加密方式
	 */
	private String paySign;

	/**
	 * 创建签名信息
	 */
	public void createPaySign() {
		// 构建签名格式，格式顺序参考微信支付官方文档，这里封装好了
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(WeChatConfig.APPID).append("\n");
		stringBuilder.append(timeStamp).append("\n");
		stringBuilder.append(nonceStr).append("\n");
		stringBuilder.append(packages).append("\n");
		try {
			// 微信小程序默认加密方式
			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initSign(WechatPayUtil.getPrivatekey());
			sign.update(stringBuilder.toString().getBytes());
			paySign = Base64.getEncoder().encodeToString(sign.sign());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取时间戳，不包含毫秒的时间戳
	 * 
	 * @return 时间戳，不包含毫秒的时间戳
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * 设置时间戳，不包含毫秒的时间戳
	 * 
	 * @param timeStamp 时间戳，不包含毫秒的时间戳
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * 链式添加时间戳，不包含毫秒的时间戳
	 * 
	 * @param timeStamp 时间戳，不包含毫秒的时间戳
	 * @return 对象本身
	 */
	public PayResult chainTimeStamp(String timeStamp) {
		setTimeStamp(timeStamp);
		return this;
	}

	/**
	 * 获取随机字符串，最大长度32
	 * 
	 * @return 随机字符串，最大长度32
	 */
	public String getNonceStr() {
		return nonceStr;
	}

	/**
	 * 设置随机字符串，最大长度32
	 * 
	 * @param nonceStr 随机字符串，最大长度32
	 */
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	/**
	 * 链式添加随机字符串，最大长度32
	 * 
	 * @param nonceStr 随机字符串，最大长度32
	 * @return 对象本身
	 */
	public PayResult chainNonceStr(String nonceStr) {
		setNonceStr(nonceStr);
		return this;
	}

	/**
	 * 获取订单详情扩展字符串，需要接收预订单的返回数据
	 * 
	 * @return 订单详情扩展字符串，需要接收预订单的返回数据
	 */
	public String getPackages() {
		return packages;
	}

	/**
	 * 设置订单详情扩展字符串，需要接收预订单的返回数据
	 * 
	 * @param packages 订单详情扩展字符串，需要接收预订单的返回数据
	 */
	public void setPackages(String packages) {
		this.packages = packages;
	}

	/**
	 * 链式添加订单详情扩展字符串，需要接收预订单的返回数据
	 * 
	 * @param packages 订单详情扩展字符串，需要接收预订单的返回数据
	 * @return 对象本身
	 */
	public PayResult chainPackages(String packages) {
		setPackages(packages);
		return this;
	}

	/**
	 * 获取签名方式，RSA默认值
	 * 
	 * @return 签名方式，RSA默认值
	 */
	public String getSignType() {
		return signType;
	}

	/**
	 * 设置签名方式，RSA默认值
	 * 
	 * @param signType 签名方式，RSA默认值
	 */
	public void setSignType(String signType) {
		this.signType = signType;
	}

	/**
	 * 链式添加签名方式，RSA默认值
	 * 
	 * @param signType 签名方式，RSA默认值
	 * @return 对象本身
	 */
	public PayResult chainSignType(String signType) {
		setSignType(signType);
		return this;
	}

	/**
	 * 获取签名，加密方式
	 * 
	 * @return 签名，加密方式
	 */
	public String getPaySign() {
		return paySign;
	}

	/**
	 * 设置签名，加密方式
	 * 
	 * @param paySign 签名，加密方式
	 */
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	/**
	 * 链式添加签名，加密方式
	 * 
	 * @param paySign 签名，加密方式
	 * @return 对象本身
	 */
	public PayResult chainPaySign(String paySign) {
		setPaySign(paySign);
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
