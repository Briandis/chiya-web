/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 优惠信息
 */
@Getter
@Setter
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

}