package chiya.web.wechat.entity;

/**
 * 微信支付金额
 */
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

	/**
	 * 获取用户支付金额
	 * 
	 * @return 用户支付金额
	 */
	public Integer getPayer_total() {
		return payer_total;
	}

	/**
	 * 设置用户支付金额
	 * 
	 * @param payer_total 用户支付金额
	 */
	public void setPayer_total(Integer payer_total) {
		this.payer_total = payer_total;
	}

	/**
	 * 链式添加用户支付金额
	 * 
	 * @param payer_total 用户支付金额
	 * @return 对象本身
	 */
	public Amount chainPayer_total(Integer payer_total) {
		setPayer_total(payer_total);
		return this;
	}

	/**
	 * 获取总金额
	 * 
	 * @return 总金额
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * 设置总金额
	 * 
	 * @param total 总金额
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * 链式添加总金额
	 * 
	 * @param total 总金额
	 * @return 对象本身
	 */
	public Amount chainTotal(Integer total) {
		setTotal(total);
		return this;
	}

	/**
	 * 获取货币类型
	 * 
	 * @return 货币类型
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 设置货币类型
	 * 
	 * @param currency 货币类型
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 链式添加货币类型
	 * 
	 * @param currency 货币类型
	 * @return 对象本身
	 */
	public Amount chainCurrency(String currency) {
		setCurrency(currency);
		return this;
	}

	/**
	 * 获取用户支付币种
	 * 
	 * @return 用户支付币种
	 */
	public String getPayer_currency() {
		return payer_currency;
	}

	/**
	 * 设置用户支付币种
	 * 
	 * @param payer_currency 用户支付币种
	 */
	public void setPayer_currency(String payer_currency) {
		this.payer_currency = payer_currency;
	}

	/**
	 * 链式添加用户支付币种
	 * 
	 * @param payer_currency 用户支付币种
	 * @return 对象本身
	 */
	public Amount chainPayer_currency(String payer_currency) {
		setPayer_currency(payer_currency);
		return this;
	}

}