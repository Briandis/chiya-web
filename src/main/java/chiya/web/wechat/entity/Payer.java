/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付对象
 */
@Getter
@Setter
public class Payer {

	/**
	 * 用户标识
	 */
	private String openid;

}