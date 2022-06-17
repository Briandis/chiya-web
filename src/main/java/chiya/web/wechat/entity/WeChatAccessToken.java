package chiya.web.wechat.entity;

import chiya.web.wechat.config.WeChatConfig;

/**
 * 微信接口调用凭证
 * 
 * @author chiya
 */
public class WeChatAccessToken {
	/** 填写 client_credential */
	private static String grant_type = "client_credential";
	/** 小程序唯一凭证，即 AppID */
	private static String appid = WeChatConfig.APPID;
	/** 小程序唯一凭证密钥，即 AppSecret */
	private static String secret = WeChatConfig.SECRET;

	/**
	 * 获取grant_type
	 *
	 * @return grant_type
	 */
	public static String getGrant_type() {
		return grant_type;
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

}
