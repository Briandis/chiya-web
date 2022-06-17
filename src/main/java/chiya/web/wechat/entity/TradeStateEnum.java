package chiya.web.wechat.entity;

import lombok.Getter;

/**
 * 微信支付订单状态枚举值
 */
@Getter
public enum TradeStateEnum {
	/**
	 * 支付成功
	 */
	SUCCESS("SUCCESS", "支付成功"),
	/**
	 * 转入退款
	 */
	REFUND("REFUND", "转入退款"),
	/**
	 * 未支付
	 */
	NOTPAY("NOTPAY", "未支付"),
	/**
	 * 已关闭
	 */
	CLOSED("CLOSED", "已关闭"),
	/**
	 * 已撤销
	 */
	REVOKED("REVOKED", "已撤销"),
	/**
	 * 用户支付中
	 */
	USERPAYING("USERPAYING", "用户支付中"),
	/**
	 * 支付失败
	 */
	PAYERROR("PAYERROR", "支付失败");

	TradeStateEnum(String tradeState, String tradeStateDesc) {
		this.tradeState = tradeState;
		this.tradeStateDesc = tradeStateDesc;
	}

	/**
	 * 交易状态码
	 */
	private String tradeState;
	/**
	 * 交易状态描述
	 */
	private String tradeStateDesc;
}
