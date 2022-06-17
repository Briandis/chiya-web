/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品详细列表
 *
 */
@Setter
@Getter
public class GoodsDetail {

	/**
	 * 商品备注
	 */
	private String goods_remark;
	/**
	 * 商品数量
	 */
	private Integer quantity;
	/**
	 * 商品优惠金额
	 */
	private Integer discount_amount;
	/**
	 * 商品编码
	 */
	private String goods_id;
	/**
	 * 商品单价
	 */
	private Integer unit_price;

}