package chiya.web.wechat.entity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import chiya.core.base.string.StringUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 检查退款返回的实体对象
 * 
 * @author chiya
 *
 */
@Getter
@Setter
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
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
