/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信支付金额
 */
@Getter
@Setter
public class Amount {
	/**
	 * 用户支付金额
	 */
	private Integer payer_total;
	/**
	 * 总金额
	 */
	private Integer total;
	/**
	 * 货币类型
	 */
	private String currency;
	/**
	 * 用户支付币种
	 */
	private String payer_currency;
}