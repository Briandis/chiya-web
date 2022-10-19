/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import com.alibaba.fastjson.JSON;

/**
 * 回调通密文数据
 */
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

	/**
	 * 获取原始类型
	 * 
	 * @return 原始类型
	 */
	public String getOriginal_type() {
		return original_type;
	}

	/**
	 * 设置原始类型
	 * 
	 * @param original_type 原始类型
	 */
	public void setOriginal_type(String original_type) {
		this.original_type = original_type;
	}

	/**
	 * 链式添加原始类型
	 * 
	 * @param original_type 原始类型
	 * @return 对象本身
	 */
	public Resource chainOriginal_type(String original_type) {
		setOriginal_type(original_type);
		return this;
	}

	/**
	 * 获取加密算法类型
	 * 
	 * @return 加密算法类型
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * 设置加密算法类型
	 * 
	 * @param algorithm 加密算法类型
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * 链式添加加密算法类型
	 * 
	 * @param algorithm 加密算法类型
	 * @return 对象本身
	 */
	public Resource chainAlgorithm(String algorithm) {
		setAlgorithm(algorithm);
		return this;
	}

	/**
	 * 获取数据密文
	 * 
	 * @return 数据密文
	 */
	public String getCiphertext() {
		return ciphertext;
	}

	/**
	 * 设置数据密文
	 * 
	 * @param ciphertext 数据密文
	 */
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}

	/**
	 * 链式添加数据密文
	 * 
	 * @param ciphertext 数据密文
	 * @return 对象本身
	 */
	public Resource chainCiphertext(String ciphertext) {
		setCiphertext(ciphertext);
		return this;
	}

	/**
	 * 获取附加数据
	 * 
	 * @return 附加数据
	 */
	public String getAssociated_data() {
		return associated_data;
	}

	/**
	 * 设置附加数据
	 * 
	 * @param associated_data 附加数据
	 */
	public void setAssociated_data(String associated_data) {
		this.associated_data = associated_data;
	}

	/**
	 * 链式添加附加数据
	 * 
	 * @param associated_data 附加数据
	 * @return 对象本身
	 */
	public Resource chainAssociated_data(String associated_data) {
		setAssociated_data(associated_data);
		return this;
	}

	/**
	 * 获取随机串
	 * 
	 * @return 随机串
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * 设置随机串
	 * 
	 * @param nonce 随机串
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	/**
	 * 链式添加随机串
	 * 
	 * @param nonce 随机串
	 * @return 对象本身
	 */
	public Resource chainNonce(String nonce) {
		setNonce(nonce);
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}