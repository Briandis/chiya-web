package chiya.web.wechat.entity;

import chiya.web.wechat.config.WeChatConfig;

/**
 * 微信登录类
 * 
 * @author chiya
 */
public class WeChatLogin {
	/** 小程序 appid */
	private static String appid = WeChatConfig.APPID;
	/** 小程序 appSecret */
	private static String secret = WeChatConfig.SECRET;
	/** 授权类型，此处只需填写 authorization_code */
	private static String grant_type = "authorization_code";
	/** 登录时获取的 code */
	private String js_code;

	/**
	 * 获取js_code
	 *
	 * @return js_code
	 */
	public String getJs_code() {
		return js_code;
	}

	/**
	 * 设置js_code
	 *
	 * @param js_code 要设置的 js_code
	 */
	public void setJs_code(String js_code) {
		this.js_code = js_code;
	}

	/**
	 * 获取appid
	 *
	 * @return appid
	 */
	public static String getAppid() {
		return appid;
	}

	/**
	 * 获取secret
	 *
	 * @return secret
	 */
	public static String getSecret() {
		return secret;
	}

	/**
	 * 获取grant_type
	 *
	 * @return grant_type
	 */
	public static String getGrant_type() {
		return grant_type;
	}

}
