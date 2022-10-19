/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import java.util.List;

import com.alibaba.fastjson.JSON;

import chiya.core.base.string.StringUtil;

import java.util.Date;

/**
 * 微信支付回调通知解密后内容
 */
public class WechatCallbackResouce {

	/**
	 * 微信支付订单号
	 */
	private String transaction_id;
	/**
	 * 商户号
	 */
	private String mchid;
	/**
	 * 交易状态
	 */
	private String trade_state;
	/**
	 * 付款银行
	 */
	private String bank_type;
	/**
	 * 支付完成时间
	 */
	private Date success_time;
	/**
	 * 商户订单号
	 */
	private String out_trade_no;
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 交易状态描述
	 */
	private String trade_state_desc;
	/**
	 * 交易类型
	 */
	private String trade_type;
	/**
	 * 附加数据
	 */
	private String attach;

	/**
	 * 场景信息
	 */
	private SceneInfo scene_info;
	/**
	 * 订单金额
	 */
	private Amount amount;
	/**
	 * 优惠功能
	 */
	private List<PromotionDetail> promotion_detail;
	/**
	 * 支付者
	 */
	private Payer payer;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	/**
	 * 检查订单是否支付成功
	 * 
	 * @return 支付成功/失败
	 */
	public boolean isPaySuccess() {
		return StringUtil.eqString(trade_state, TradeStateEnum.SUCCESS.getTradeState());
	}

	/**
	 * 获取微信支付订单号
	 * 
	 * @return 微信支付订单号
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * 设置微信支付订单号
	 * 
	 * @param transaction_id 微信支付订单号
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * 链式添加微信支付订单号
	 * 
	 * @param transaction_id 微信支付订单号
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainTransaction_id(String transaction_id) {
		setTransaction_id(transaction_id);
		return this;
	}

	/**
	 * 获取商户号
	 * 
	 * @return 商户号
	 */
	public String getMchid() {
		return mchid;
	}

	/**
	 * 设置商户号
	 * 
	 * @param mchid 商户号
	 */
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	/**
	 * 链式添加商户号
	 * 
	 * @param mchid 商户号
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainMchid(String mchid) {
		setMchid(mchid);
		return this;
	}

	/**
	 * 获取交易状态
	 * 
	 * @return 交易状态
	 */
	public String getTrade_state() {
		return trade_state;
	}

	/**
	 * 设置交易状态
	 * 
	 * @param trade_state 交易状态
	 */
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	/**
	 * 链式添加交易状态
	 * 
	 * @param trade_state 交易状态
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainTrade_state(String trade_state) {
		setTrade_state(trade_state);
		return this;
	}

	/**
	 * 获取付款银行
	 * 
	 * @return 付款银行
	 */
	public String getBank_type() {
		return bank_type;
	}

	/**
	 * 设置付款银行
	 * 
	 * @param bank_type 付款银行
	 */
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	/**
	 * 链式添加付款银行
	 * 
	 * @param bank_type 付款银行
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainBank_type(String bank_type) {
		setBank_type(bank_type);
		return this;
	}

	/**
	 * 获取支付完成时间
	 * 
	 * @return 支付完成时间
	 */
	public Date getSuccess_time() {
		return success_time;
	}

	/**
	 * 设置支付完成时间
	 * 
	 * @param success_time 支付完成时间
	 */
	public void setSuccess_time(Date success_time) {
		this.success_time = success_time;
	}

	/**
	 * 链式添加支付完成时间
	 * 
	 * @param success_time 支付完成时间
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainSuccess_time(Date success_time) {
		setSuccess_time(success_time);
		return this;
	}

	/**
	 * 获取商户订单号
	 * 
	 * @return 商户订单号
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * 设置商户订单号
	 * 
	 * @param out_trade_no 商户订单号
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * 链式添加商户订单号
	 * 
	 * @param out_trade_no 商户订单号
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainOut_trade_no(String out_trade_no) {
		setOut_trade_no(out_trade_no);
		return this;
	}

	/**
	 * 获取应用ID
	 * 
	 * @return 应用ID
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * 设置应用ID
	 * 
	 * @param appid 应用ID
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * 链式添加应用ID
	 * 
	 * @param appid 应用ID
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainAppid(String appid) {
		setAppid(appid);
		return this;
	}

	/**
	 * 获取交易状态描述
	 * 
	 * @return 交易状态描述
	 */
	public String getTrade_state_desc() {
		return trade_state_desc;
	}

	/**
	 * 设置交易状态描述
	 * 
	 * @param trade_state_desc 交易状态描述
	 */
	public void setTrade_state_desc(String trade_state_desc) {
		this.trade_state_desc = trade_state_desc;
	}

	/**
	 * 链式添加交易状态描述
	 * 
	 * @param trade_state_desc 交易状态描述
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainTrade_state_desc(String trade_state_desc) {
		setTrade_state_desc(trade_state_desc);
		return this;
	}

	/**
	 * 获取交易类型
	 * 
	 * @return 交易类型
	 */
	public String getTrade_type() {
		return trade_type;
	}

	/**
	 * 设置交易类型
	 * 
	 * @param trade_type 交易类型
	 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	/**
	 * 链式添加交易类型
	 * 
	 * @param trade_type 交易类型
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainTrade_type(String trade_type) {
		setTrade_type(trade_type);
		return this;
	}

	/**
	 * 获取附加数据
	 * 
	 * @return 附加数据
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * 设置附加数据
	 * 
	 * @param attach 附加数据
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * 链式添加附加数据
	 * 
	 * @param attach 附加数据
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainAttach(String attach) {
		setAttach(attach);
		return this;
	}

	/**
	 * 获取场景信息
	 * 
	 * @return 场景信息
	 */
	public SceneInfo getScene_info() {
		return scene_info;
	}

	/**
	 * 设置场景信息
	 * 
	 * @param scene_info 场景信息
	 */
	public void setScene_info(SceneInfo scene_info) {
		this.scene_info = scene_info;
	}

	/**
	 * 链式添加场景信息
	 * 
	 * @param scene_info 场景信息
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainScene_info(SceneInfo scene_info) {
		setScene_info(scene_info);
		return this;
	}

	/**
	 * 获取订单金额
	 * 
	 * @return 订单金额
	 */
	public Amount getAmount() {
		return amount;
	}

	/**
	 * 设置订单金额
	 * 
	 * @param amount 订单金额
	 */
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	/**
	 * 链式添加订单金额
	 * 
	 * @param amount 订单金额
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainAmount(Amount amount) {
		setAmount(amount);
		return this;
	}

	/**
	 * 获取优惠功能
	 * 
	 * @return 优惠功能
	 */
	public List<PromotionDetail> getPromotion_detail() {
		return promotion_detail;
	}

	/**
	 * 设置优惠功能
	 * 
	 * @param promotion_detail 优惠功能
	 */
	public void setPromotion_detail(List<PromotionDetail> promotion_detail) {
		this.promotion_detail = promotion_detail;
	}

	/**
	 * 链式添加优惠功能
	 * 
	 * @param promotion_detail 优惠功能
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainPromotion_detail(List<PromotionDetail> promotion_detail) {
		setPromotion_detail(promotion_detail);
		return this;
	}

	/**
	 * 获取支付者
	 * 
	 * @return 支付者
	 */
	public Payer getPayer() {
		return payer;
	}

	/**
	 * 设置支付者
	 * 
	 * @param payer 支付者
	 */
	public void setPayer(Payer payer) {
		this.payer = payer;
	}

	/**
	 * 链式添加支付者
	 * 
	 * @param payer 支付者
	 * @return 对象本身
	 */
	public WechatCallbackResouce chainPayer(Payer payer) {
		setPayer(payer);
		return this;
	}

}