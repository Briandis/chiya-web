package chiya.web.wechat.entity;

/**
 * 手机号实体
 * 
 * @author chiya
 */
public class PhoneCodeEntity {
	/** 手机号 */
	private String code;

	public PhoneCodeEntity(String code) {
		this.code = code;
	}

	/**
	 * 获取手机号
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置
	 * 
	 * @param code 手机号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 链式添加
	 * 
	 * @param code 手机号
	 * @return 对象本身
	 */
	public PhoneCodeEntity chainCode(String code) {
		setCode(code);
		return this;
	}

}
