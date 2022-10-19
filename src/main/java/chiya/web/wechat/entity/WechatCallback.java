package chiya.web.wechat.entity;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 支付通知回调
 */
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
	 * 
	 * @return
	 */
	public Resource toResource() {
		return JSONObject.parseObject(resource, Resource.class);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	/**
	 * 获取通知id
	 * 
	 * @return 通知id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置通知id
	 * 
	 * @param id 通知id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 链式添加通知id
	 * 
	 * @param id 通知id
	 * @return 对象本身
	 */
	public WechatCallback chainId(String id) {
		setId(id);
		return this;
	}

	/**
	 * 获取通知的时间
	 * 
	 * @return 通知的时间
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * 设置通知的时间
	 * 
	 * @param create_time 通知的时间
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * 链式添加通知的时间
	 * 
	 * @param create_time 通知的时间
	 * @return 对象本身
	 */
	public WechatCallback chainCreate_time(Date create_time) {
		setCreate_time(create_time);
		return this;
	}

	/**
	 * 获取通知数据类型
	 * 
	 * @return 通知数据类型
	 */
	public String getResource_type() {
		return resource_type;
	}

	/**
	 * 设置通知数据类型
	 * 
	 * @param resource_type 通知数据类型
	 */
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}

	/**
	 * 链式添加通知数据类型
	 * 
	 * @param resource_type 通知数据类型
	 * @return 对象本身
	 */
	public WechatCallback chainResource_type(String resource_type) {
		setResource_type(resource_type);
		return this;
	}

	/**
	 * 获取通知类型
	 * 
	 * @return 通知类型
	 */
	public String getEvent_type() {
		return event_type;
	}

	/**
	 * 设置通知类型
	 * 
	 * @param event_type 通知类型
	 */
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	/**
	 * 链式添加通知类型
	 * 
	 * @param event_type 通知类型
	 * @return 对象本身
	 */
	public WechatCallback chainEvent_type(String event_type) {
		setEvent_type(event_type);
		return this;
	}

	/**
	 * 获取回调摘要
	 * 
	 * @return 回调摘要
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * 设置回调摘要
	 * 
	 * @param summary 回调摘要
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * 链式添加回调摘要
	 * 
	 * @param summary 回调摘要
	 * @return 对象本身
	 */
	public WechatCallback chainSummary(String summary) {
		setSummary(summary);
		return this;
	}

	/**
	 * 获取通知数据
	 * 
	 * @return 通知数据
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * 设置通知数据
	 * 
	 * @param resource 通知数据
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * 链式添加通知数据
	 * 
	 * @param resource 通知数据
	 * @return 对象本身
	 */
	public WechatCallback chainResource(String resource) {
		setResource(resource);
		return this;
	}

}