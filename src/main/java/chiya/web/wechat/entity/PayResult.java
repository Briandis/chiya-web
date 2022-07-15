package chiya.web.wechat.entity;

import java.security.Signature;
import java.util.Base64;

import com.alibaba.fastjson.JSON;

import chiya.core.base.random.RandomString;
import chiya.web.wechat.config.WeChatConfig;
import chiya.web.wechat.util.WechatPayUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付单返回的信息，给前台调用支付接口使用
 * 
 * @author brian
 *
 */
@Getter
@Setter
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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
