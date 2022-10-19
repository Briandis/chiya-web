package chiya.web.wechat.entity;

/**
 * 支付对象
 */
public class Payer {

	/**
	 * 用户标识
	 */
	private String openid;

	/**
	 * 获取用户标识
	 * 
	 * @return 用户标识
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * 设置用户标识
	 * 
	 * @param openid 用户标识
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 链式添加用户标识
	 * 
	 * @param openid 用户标识
	 * @return 对象本身
	 */
	public Payer chainOpenid(String openid) {
		setOpenid(openid);
		return this;
	}

}