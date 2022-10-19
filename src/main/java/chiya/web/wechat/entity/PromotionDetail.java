/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import java.util.Date;
import java.util.List;

/**
 * 优惠信息
 */
public class PromotionDetail {

	/**
	 * 优惠券面额
	 */
	private Integer amount;

	/**
	 * 微信出资
	 */
	private Integer wechatpay_contribute;
	/**
	 * 券ID
	 */
	private String coupon_id;
	/**
	 * 优惠范围
	 */
	private String scope;
	/**
	 * 商户出资
	 */
	private Integer merchant_contribute;
	/**
	 * 优惠名称
	 */
	private Date name;
	/**
	 * 其他出资
	 */
	private Integer other_contribute;
	/**
	 * 优惠币种
	 */
	private String currency;
	/**
	 * 活动ID
	 */
	private String stock_id;
	/**
	 * 优惠类型
	 */
	private String type;
	/**
	 * 单品列表
	 */
	private List<GoodsDetail> goods_detail;

	/**
	 * 获取优惠券面额
	 * 
	 * @return 优惠券面额
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * 设置优惠券面额
	 * 
	 * @param amount 优惠券面额
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * 链式添加优惠券面额
	 * 
	 * @param amount 优惠券面额
	 * @return 对象本身
	 */
	public PromotionDetail chainAmount(Integer amount) {
		setAmount(amount);
		return this;
	}

	/**
	 * 获取微信出资
	 * 
	 * @return 微信出资
	 */
	public Integer getWechatpay_contribute() {
		return wechatpay_contribute;
	}

	/**
	 * 设置微信出资
	 * 
	 * @param wechatpay_contribute 微信出资
	 */
	public void setWechatpay_contribute(Integer wechatpay_contribute) {
		this.wechatpay_contribute = wechatpay_contribute;
	}

	/**
	 * 链式添加微信出资
	 * 
	 * @param wechatpay_contribute 微信出资
	 * @return 对象本身
	 */
	public PromotionDetail chainWechatpay_contribute(Integer wechatpay_contribute) {
		setWechatpay_contribute(wechatpay_contribute);
		return this;
	}

	/**
	 * 获取券ID
	 * 
	 * @return 券ID
	 */
	public String getCoupon_id() {
		return coupon_id;
	}

	/**
	 * 设置券ID
	 * 
	 * @param coupon_id 券ID
	 */
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	/**
	 * 链式添加券ID
	 * 
	 * @param coupon_id 券ID
	 * @return 对象本身
	 */
	public PromotionDetail chainCoupon_id(String coupon_id) {
		setCoupon_id(coupon_id);
		return this;
	}

	/**
	 * 获取优惠范围
	 * 
	 * @return 优惠范围
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * 设置优惠范围
	 * 
	 * @param scope 优惠范围
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * 链式添加优惠范围
	 * 
	 * @param scope 优惠范围
	 * @return 对象本身
	 */
	public PromotionDetail chainScope(String scope) {
		setScope(scope);
		return this;
	}

	/**
	 * 获取商户出资
	 * 
	 * @return 商户出资
	 */
	public Integer getMerchant_contribute() {
		return merchant_contribute;
	}

	/**
	 * 设置商户出资
	 * 
	 * @param merchant_contribute 商户出资
	 */
	public void setMerchant_contribute(Integer merchant_contribute) {
		this.merchant_contribute = merchant_contribute;
	}

	/**
	 * 链式添加商户出资
	 * 
	 * @param merchant_contribute 商户出资
	 * @return 对象本身
	 */
	public PromotionDetail chainMerchant_contribute(Integer merchant_contribute) {
		setMerchant_contribute(merchant_contribute);
		return this;
	}

	/**
	 * 获取优惠名称
	 * 
	 * @return 优惠名称
	 */
	public Date getName() {
		return name;
	}

	/**
	 * 设置优惠名称
	 * 
	 * @param name 优惠名称
	 */
	public void setName(Date name) {
		this.name = name;
	}

	/**
	 * 链式添加优惠名称
	 * 
	 * @param name 优惠名称
	 * @return 对象本身
	 */
	public PromotionDetail chainName(Date name) {
		setName(name);
		return this;
	}

	/**
	 * 获取其他出资
	 * 
	 * @return 其他出资
	 */
	public Integer getOther_contribute() {
		return other_contribute;
	}

	/**
	 * 设置其他出资
	 * 
	 * @param other_contribute 其他出资
	 */
	public void setOther_contribute(Integer other_contribute) {
		this.other_contribute = other_contribute;
	}

	/**
	 * 链式添加其他出资
	 * 
	 * @param other_contribute 其他出资
	 * @return 对象本身
	 */
	public PromotionDetail chainOther_contribute(Integer other_contribute) {
		setOther_contribute(other_contribute);
		return this;
	}

	/**
	 * 获取优惠币种
	 * 
	 * @return 优惠币种
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 设置优惠币种
	 * 
	 * @param currency 优惠币种
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 链式添加优惠币种
	 * 
	 * @param currency 优惠币种
	 * @return 对象本身
	 */
	public PromotionDetail chainCurrency(String currency) {
		setCurrency(currency);
		return this;
	}

	/**
	 * 获取活动ID
	 * 
	 * @return 活动ID
	 */
	public String getStock_id() {
		return stock_id;
	}

	/**
	 * 设置活动ID
	 * 
	 * @param stock_id 活动ID
	 */
	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}

	/**
	 * 链式添加活动ID
	 * 
	 * @param stock_id 活动ID
	 * @return 对象本身
	 */
	public PromotionDetail chainStock_id(String stock_id) {
		setStock_id(stock_id);
		return this;
	}

	/**
	 * 获取优惠类型
	 * 
	 * @return 优惠类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置优惠类型
	 * 
	 * @param type 优惠类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 链式添加优惠类型
	 * 
	 * @param type 优惠类型
	 * @return 对象本身
	 */
	public PromotionDetail chainType(String type) {
		setType(type);
		return this;
	}

	/**
	 * 获取单品列表
	 * 
	 * @return 单品列表
	 */
	public List<GoodsDetail> getGoods_detail() {
		return goods_detail;
	}

	/**
	 * 设置单品列表
	 * 
	 * @param goods_detail 单品列表
	 */
	public void setGoods_detail(List<GoodsDetail> goods_detail) {
		this.goods_detail = goods_detail;
	}

	/**
	 * 链式添加单品列表
	 * 
	 * @param goods_detail 单品列表
	 * @return 对象本身
	 */
	public PromotionDetail chainGoods_detail(List<GoodsDetail> goods_detail) {
		setGoods_detail(goods_detail);
		return this;
	}

}