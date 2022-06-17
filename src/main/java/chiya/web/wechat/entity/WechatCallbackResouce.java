/**
  * Copyright 2021 bejson.com 
  */
package chiya.web.wechat.entity;

import java.util.List;

import com.alibaba.fastjson.JSON;

import chiya.core.base.string.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 微信支付回调通知解密后内容
 */
@Setter
@Getter
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

}