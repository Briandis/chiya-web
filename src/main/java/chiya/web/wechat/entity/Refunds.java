package chiya.web.wechat.entity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import chiya.core.base.string.StringUtil;

/**
 * 检查退款返回的实体对象
 * 
 * @author chiya
 *
 */
public class Refunds {
	/**
	 * 微信支付退款单号
	 */
	private String refund_id;

	/**
	 * 商户退款单号
	 */
	private String out_refund_no;
	/**
	 * 微信支付订单号
	 */
	private String transaction_id;
	/**
	 * 商户订单号
	 */
	private String out_trade_no;
	/**
	 * 退款渠道
	 */
	private String channel;
	/**
	 * 退款入账账户
	 */
	private String user_received_account;
	/**
	 * 退款成功时间
	 */
	private Date success_time;
	/**
	 * 退款创建时间
	 */
	private Date create_time;
	/**
	 * 退款状态
	 */
	private String status;
	/**
	 * 资金账户
	 */
	private String funds_account;
	/**
	 * 金额信息
	 */
	private Amount amount;
	/**
	 * 优惠退款信息
	 */
	private List<PromotionDetail> promotion_detail;

	/**
	 * 退款是否成功
	 * 
	 * @return 成功/失败
	 */
	public boolean isSuccess() {
		return StringUtil.eqString(status, TradeStateEnum.SUCCESS.getTradeState());
	}

	/**
	 * 获取微信支付退款单号
	 * 
	 * @return 微信支付退款单号
	 */
	public String getRefund_id() {
		return refund_id;
	}

	/**
	 * 设置微信支付退款单号
	 * 
	 * @param refund_id 微信支付退款单号
	 */
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	/**
	 * 链式添加微信支付退款单号
	 * 
	 * @param refund_id 微信支付退款单号
	 * @return 对象本身
	 */
	public Refunds chainRefund_id(String refund_id) {
		setRefund_id(refund_id);
		return this;
	}

	/**
	 * 获取商户退款单号
	 * 
	 * @return 商户退款单号
	 */
	public String getOut_refund_no() {
		return out_refund_no;
	}

	/**
	 * 设置商户退款单号
	 * 
	 * @param out_refund_no 商户退款单号
	 */
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	/**
	 * 链式添加商户退款单号
	 * 
	 * @param out_refund_no 商户退款单号
	 * @return 对象本身
	 */
	public Refunds chainOut_refund_no(String out_refund_no) {
		setOut_refund_no(out_refund_no);
		return this;
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
	public Refunds chainTransaction_id(String transaction_id) {
		setTransaction_id(transaction_id);
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
	public Refunds chainOut_trade_no(String out_trade_no) {
		setOut_trade_no(out_trade_no);
		return this;
	}

	/**
	 * 获取退款渠道
	 * 
	 * @return 退款渠道
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * 设置退款渠道
	 * 
	 * @param channel 退款渠道
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * 链式添加退款渠道
	 * 
	 * @param channel 退款渠道
	 * @return 对象本身
	 */
	public Refunds chainChannel(String channel) {
		setChannel(channel);
		return this;
	}

	/**
	 * 获取退款入账账户
	 * 
	 * @return 退款入账账户
	 */
	public String getUser_received_account() {
		return user_received_account;
	}

	/**
	 * 设置退款入账账户
	 * 
	 * @param user_received_account 退款入账账户
	 */
	public void setUser_received_account(String user_received_account) {
		this.user_received_account = user_received_account;
	}

	/**
	 * 链式添加退款入账账户
	 * 
	 * @param user_received_account 退款入账账户
	 * @return 对象本身
	 */
	public Refunds chainUser_received_account(String user_received_account) {
		setUser_received_account(user_received_account);
		return this;
	}

	/**
	 * 获取退款成功时间
	 * 
	 * @return 退款成功时间
	 */
	public Date getSuccess_time() {
		return success_time;
	}

	/**
	 * 设置退款成功时间
	 * 
	 * @param success_time 退款成功时间
	 */
	public void setSuccess_time(Date success_time) {
		this.success_time = success_time;
	}

	/**
	 * 链式添加退款成功时间
	 * 
	 * @param success_time 退款成功时间
	 * @return 对象本身
	 */
	public Refunds chainSuccess_time(Date success_time) {
		setSuccess_time(success_time);
		return this;
	}

	/**
	 * 获取退款创建时间
	 * 
	 * @return 退款创建时间
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * 设置退款创建时间
	 * 
	 * @param create_time 退款创建时间
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * 链式添加退款创建时间
	 * 
	 * @param create_time 退款创建时间
	 * @return 对象本身
	 */
	public Refunds chainCreate_time(Date create_time) {
		setCreate_time(create_time);
		return this;
	}

	/**
	 * 获取退款状态
	 * 
	 * @return 退款状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置退款状态
	 * 
	 * @param status 退款状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 链式添加退款状态
	 * 
	 * @param status 退款状态
	 * @return 对象本身
	 */
	public Refunds chainStatus(String status) {
		setStatus(status);
		return this;
	}

	/**
	 * 获取资金账户
	 * 
	 * @return 资金账户
	 */
	public String getFunds_account() {
		return funds_account;
	}

	/**
	 * 设置资金账户
	 * 
	 * @param funds_account 资金账户
	 */
	public void setFunds_account(String funds_account) {
		this.funds_account = funds_account;
	}

	/**
	 * 链式添加资金账户
	 * 
	 * @param funds_account 资金账户
	 * @return 对象本身
	 */
	public Refunds chainFunds_account(String funds_account) {
		setFunds_account(funds_account);
		return this;
	}

	/**
	 * 获取金额信息
	 * 
	 * @return 金额信息
	 */
	public Amount getAmount() {
		return amount;
	}

	/**
	 * 设置金额信息
	 * 
	 * @param amount 金额信息
	 */
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	/**
	 * 链式添加金额信息
	 * 
	 * @param amount 金额信息
	 * @return 对象本身
	 */
	public Refunds chainAmount(Amount amount) {
		setAmount(amount);
		return this;
	}

	/**
	 * 获取优惠退款信息
	 * 
	 * @return 优惠退款信息
	 */
	public List<PromotionDetail> getPromotion_detail() {
		return promotion_detail;
	}

	/**
	 * 设置优惠退款信息
	 * 
	 * @param promotion_detail 优惠退款信息
	 */
	public void setPromotion_detail(List<PromotionDetail> promotion_detail) {
		this.promotion_detail = promotion_detail;
	}

	/**
	 * 链式添加优惠退款信息
	 * 
	 * @param promotion_detail 优惠退款信息
	 * @return 对象本身
	 */
	public Refunds chainPromotion_detail(List<PromotionDetail> promotion_detail) {
		setPromotion_detail(promotion_detail);
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
