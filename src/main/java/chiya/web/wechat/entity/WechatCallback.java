/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付通知回调
 */
@Getter
@Setter
public class WechatCallback {

	/**
	 * 通知id
	 */
	private String id;
	/**
	 * 通知的时间
	 */
	private Date create_time;
	/**
	 * 通知数据类型
	 */
	private String resource_type;
	/**
	 * 通知类型
	 */
	private String event_type;
	/**
	 * 回调摘要
	 */
	private String summary;
	/**
	 * 通知数据
	 */
	private String resource;
	
	/**
	 * 获取解密内容
	 * @return
	 */
	public Resource toResource() {
		return JSONObject.parseObject(resource,Resource.class);
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}