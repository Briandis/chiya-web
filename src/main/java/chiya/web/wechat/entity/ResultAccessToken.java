package chiya.web.wechat.entity;

/**
 * AccessToken的回调返回对象
 * 
 * @author chiya
 */
public class ResultAccessToken {
	/** 获取到的凭证 */
	private String access_token;
	/** 凭证有效时间，单位：秒。目前是7200秒之内的值。 */
	private Integer expires_in;
	/** 错误码 */
	private Integer errcode;
	/** 错误信息 */
	private String errmsg;

	/**
	 * 获取获取到的凭证
	 * 
	 * @return 获取到的凭证
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * 设置获取到的凭证
	 * 
	 * @param access_token 获取到的凭证
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 链式添加获取到的凭证
	 * 
	 * @param access_token 获取到的凭证
	 * @return 对象本身
	 */
	public ResultAccessToken chainAccess_token(String access_token) {
		setAccess_token(access_token);
		return this;
	}

	/**
	 * 获取凭证有效时间，单位：秒。目前是7200秒之内的值。
	 * 
	 * @return 凭证有效时间，单位：秒。目前是7200秒之内的值。
	 */
	public Integer getExpires_in() {
		return expires_in;
	}

	/**
	 * 设置凭证有效时间，单位：秒。目前是7200秒之内的值。
	 * 
	 * @param expires_in 凭证有效时间，单位：秒。目前是7200秒之内的值。
	 */
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	/**
	 * 链式添加凭证有效时间，单位：秒。目前是7200秒之内的值。
	 * 
	 * @param expires_in 凭证有效时间，单位：秒。目前是7200秒之内的值。
	 * @return 对象本身
	 */
	public ResultAccessToken chainExpires_in(Integer expires_in) {
		setExpires_in(expires_in);
		return this;
	}

	/**
	 * 获取错误码
	 * 
	 * @return 错误码
	 */
	public Integer getErrcode() {
		return errcode;
	}

	/**
	 * 设置错误码
	 * 
	 * @param errcode 错误码
	 */
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	/**
	 * 链式添加错误码
	 * 
	 * @param errcode 错误码
	 * @return 对象本身
	 */
	public ResultAccessToken chainErrcode(Integer errcode) {
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
	public ResultAccessToken chainErrmsg(String errmsg) {
		setErrmsg(errmsg);
		return this;
	}

}
