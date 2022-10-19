/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

/**
 * 商品详细列表
 *
 */
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

	/**
	 * 获取商品备注
	 * 
	 * @return 商品备注
	 */
	public String getGoods_remark() {
		return goods_remark;
	}

	/**
	 * 设置商品备注
	 * 
	 * @param goods_remark 商品备注
	 */
	public void setGoods_remark(String goods_remark) {
		this.goods_remark = goods_remark;
	}

	/**
	 * 链式添加商品备注
	 * 
	 * @param goods_remark 商品备注
	 * @return 对象本身
	 */
	public GoodsDetail chainGoods_remark(String goods_remark) {
		setGoods_remark(goods_remark);
		return this;
	}

	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置商品数量
	 * 
	 * @param quantity 商品数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 链式添加商品数量
	 * 
	 * @param quantity 商品数量
	 * @return 对象本身
	 */
	public GoodsDetail chainQuantity(Integer quantity) {
		setQuantity(quantity);
		return this;
	}

	/**
	 * 获取商品优惠金额
	 * 
	 * @return 商品优惠金额
	 */
	public Integer getDiscount_amount() {
		return discount_amount;
	}

	/**
	 * 设置商品优惠金额
	 * 
	 * @param discount_amount 商品优惠金额
	 */
	public void setDiscount_amount(Integer discount_amount) {
		this.discount_amount = discount_amount;
	}

	/**
	 * 链式添加商品优惠金额
	 * 
	 * @param discount_amount 商品优惠金额
	 * @return 对象本身
	 */
	public GoodsDetail chainDiscount_amount(Integer discount_amount) {
		setDiscount_amount(discount_amount);
		return this;
	}

	/**
	 * 获取商品编码
	 * 
	 * @return 商品编码
	 */
	public String getGoods_id() {
		return goods_id;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param goods_id 商品编码
	 */
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	/**
	 * 链式添加商品编码
	 * 
	 * @param goods_id 商品编码
	 * @return 对象本身
	 */
	public GoodsDetail chainGoods_id(String goods_id) {
		setGoods_id(goods_id);
		return this;
	}

	/**
	 * 获取商品单价
	 * 
	 * @return 商品单价
	 */
	public Integer getUnit_price() {
		return unit_price;
	}

	/**
	 * 设置商品单价
	 * 
	 * @param unit_price 商品单价
	 */
	public void setUnit_price(Integer unit_price) {
		this.unit_price = unit_price;
	}

	/**
	 * 链式添加商品单价
	 * 
	 * @param unit_price 商品单价
	 * @return 对象本身
	 */
	public GoodsDetail chainUnit_price(Integer unit_price) {
		setUnit_price(unit_price);
		return this;
	}

}