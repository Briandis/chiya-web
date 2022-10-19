package chiya.web.wechat.entity;

import com.alibaba.fastjson.JSON;

/**
 * 用户信息解密后数据
 * 
 * @author chiya
 */
public class EncryptedData {
	/** openID */
	private String openId;
	/** 昵称 */
	private String nickName;
	/** 性别 */
	private String gender;
	/** 城市 */
	private String city;
	/** 省份 */
	private String province;
	/** 国家 */
	private String country;
	/** 头像地址 */
	private String avatarUrl;
	/** 唯一性ID */
	private String unionId;
	/** 微信水印 */
	private Watermark watermark;

	/**
	 * 获取openID
	 * 
	 * @return openID
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * 设置openID
	 * 
	 * @param openId openID
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 链式添加openID
	 * 
	 * @param openId openID
	 * @return 对象本身
	 */
	public EncryptedData chainOpenId(String openId) {
		setOpenId(openId);
		return this;
	}

	/**
	 * 获取昵称
	 * 
	 * @return 昵称
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 设置昵称
	 * 
	 * @param nickName 昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 链式添加昵称
	 * 
	 * @param nickName 昵称
	 * @return 对象本身
	 */
	public EncryptedData chainNickName(String nickName) {
		setNickName(nickName);
		return this;
	}

	/**
	 * 获取性别
	 * 
	 * @return 性别
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * 设置性别
	 * 
	 * @param gender 性别
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * 链式添加性别
	 * 
	 * @param gender 性别
	 * @return 对象本身
	 */
	public EncryptedData chainGender(String gender) {
		setGender(gender);
		return this;
	}

	/**
	 * 获取城市
	 * 
	 * @return 城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置城市
	 * 
	 * @param city 城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 链式添加城市
	 * 
	 * @param city 城市
	 * @return 对象本身
	 */
	public EncryptedData chainCity(String city) {
		setCity(city);
		return this;
	}

	/**
	 * 获取省份
	 * 
	 * @return 省份
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 设置省份
	 * 
	 * @param province 省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 链式添加省份
	 * 
	 * @param province 省份
	 * @return 对象本身
	 */
	public EncryptedData chainProvince(String province) {
		setProvince(province);
		return this;
	}

	/**
	 * 获取国家
	 * 
	 * @return 国家
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 设置国家
	 * 
	 * @param country 国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 链式添加国家
	 * 
	 * @param country 国家
	 * @return 对象本身
	 */
	public EncryptedData chainCountry(String country) {
		setCountry(country);
		return this;
	}

	/**
	 * 获取头像地址
	 * 
	 * @return 头像地址
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * 设置头像地址
	 * 
	 * @param avatarUrl 头像地址
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * 链式添加头像地址
	 * 
	 * @param avatarUrl 头像地址
	 * @return 对象本身
	 */
	public EncryptedData chainAvatarUrl(String avatarUrl) {
		setAvatarUrl(avatarUrl);
		return this;
	}

	/**
	 * 获取唯一性ID
	 * 
	 * @return 唯一性ID
	 */
	public String getUnionId() {
		return unionId;
	}

	/**
	 * 设置唯一性ID
	 * 
	 * @param unionId 唯一性ID
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * 链式添加唯一性ID
	 * 
	 * @param unionId 唯一性ID
	 * @return 对象本身
	 */
	public EncryptedData chainUnionId(String unionId) {
		setUnionId(unionId);
		return this;
	}

	/**
	 * 获取微信水印
	 * 
	 * @return 微信水印
	 */
	public Watermark getWatermark() {
		return watermark;
	}

	/**
	 * 设置微信水印
	 * 
	 * @param watermark 微信水印
	 */
	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}

	/**
	 * 链式添加微信水印
	 * 
	 * @param watermark 微信水印
	 * @return 对象本身
	 */
	public EncryptedData chainWatermark(Watermark watermark) {
		setWatermark(watermark);
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
