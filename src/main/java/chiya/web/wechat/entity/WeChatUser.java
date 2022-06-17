package chiya.web.wechat.entity;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信登录返回的对象
 * 
 * @author chiya
 *
 */
@Getter
@Setter
public class WeChatUser {
	/**
	 * 用户唯一标识
	 */
	private String openid;
	/**
	 * 会话密钥
	 */
	private String session_key;
	/**
	 * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	 */
	private String unionid;
	/**
	 * 错误码
	 */
	private String errcode;
	/**
	 * 错误信息
	 */
	private String errmsg;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	/**
	 * 是否登录成功
	 * 
	 * @return 成功/失败
	 */
	public boolean isLoginSuccess() {
		// 只有请求状态码等于0的时候成功
		return "0".equals(errcode);
	}

}
