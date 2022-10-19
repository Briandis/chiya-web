package chiya.web.wechat.entity;

import com.alibaba.fastjson.JSON;

/**
 * 微信登录返回的对象
 * 
 * @author chiya
 *
 */
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

	/**
	 * 获取用户唯一标识
	 * 
	 * @return 用户唯一标识
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * 设置用户唯一标识
	 * 
	 * @param openid 用户唯一标识
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 链式添加用户唯一标识
	 * 
	 * @param openid 用户唯一标识
	 * @return 对象本身
	 */
	public WeChatUser chainOpenid(String openid) {
		setOpenid(openid);
		return this;
	}

	/**
	 * 获取会话密钥
	 * 
	 * @return 会话密钥
	 */
	public String getSession_key() {
		return session_key;
	}

	/**
	 * 设置会话密钥
	 * 
	 * @param session_key 会话密钥
	 */
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	/**
	 * 链式添加会话密钥
	 * 
	 * @param session_key 会话密钥
	 * @return 对象本身
	 */
	public WeChatUser chainSession_key(String session_key) {
		setSession_key(session_key);
		return this;
	}

	/**
	 * 获取用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	 * 
	 * @return 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * 设置用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	 * 
	 * @param unionid 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/**
	 * 链式添加用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	 * 
	 * @param unionid 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
	 * @return 对象本身
	 */
	public WeChatUser chainUnionid(String unionid) {
		setUnionid(unionid);
		return this;
	}

	/**
	 * 获取错误码
	 * 
	 * @return 错误码
	 */
	public String getErrcode() {
		return errcode;
	}

	/**
	 * 设置错误码
	 * 
	 * @param errcode 错误码
	 */
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	/**
	 * 链式添加错误码
	 * 
	 * @param errcode 错误码
	 * @return 对象本身
	 */
	public WeChatUser chainErrcode(String errcode) {
		setErrcode(errcode);
		return this;
	}

	/**
	 * 获取错误信息
	 * 
	 * @return 错误信息
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * 设置错误信息
	 * 
	 * @param errmsg 错误信息
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * 链式添加错误信息
	 * 
	 * @param errmsg 错误信息
	 * @return 对象本身
	 */
	public WeChatUser chainErrmsg(String errmsg) {
		setErrmsg(errmsg);
		return this;
	}

}
