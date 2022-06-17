/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 回调通密文数据
 */
@Getter
@Setter
public class Resource {

	/**
	 * 原始类型
	 */
	private String original_type;
	/**
	 * 加密算法类型
	 */
	private String algorithm;
	/**
	 * 数据密文
	 */
	private String ciphertext;
	/**
	 * 附加数据
	 */
	private String associated_data;
	/**
	 * 随机串
	 */
	private String nonce;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}