package chiya.web.wechat.entity;

import java.util.Date;

/**
 * 微信水印
 * 
 * @author chiya
 */
public class Watermark {
	/** 用户的openId */
	private String openId;
	/** 用户时间戳 */
	private Date timestamp;

	/**
	 * 获取用户的openId
	 *
	 * @return 用户的openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * 设置用户的openId
	 *
	 * @param openId 用户的openId
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 获取用户时间戳
	 *
	 * @return 用户时间戳
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * 设置用户时间戳
	 *
	 * @param timestamp 用户时间戳
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
