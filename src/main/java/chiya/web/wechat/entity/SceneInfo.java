package chiya.web.wechat.entity;

/**
 * 场景信息
 */
public class SceneInfo {

	/**
	 * 商户端设备号
	 */
	private String device_id;

	/**
	 * 获取商户端设备号
	 * 
	 * @return 商户端设备号
	 */
	public String getDevice_id() {
		return device_id;
	}

	/**
	 * 设置商户端设备号
	 * 
	 * @param device_id 商户端设备号
	 */
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	/**
	 * 链式添加商户端设备号
	 * 
	 * @param device_id 商户端设备号
	 * @return 对象本身
	 */
	public SceneInfo chainDevice_id(String device_id) {
		setDevice_id(device_id);
		return this;
	}

}