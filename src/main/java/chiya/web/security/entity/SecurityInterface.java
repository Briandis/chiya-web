package chiya.web.security.entity;

/**
 * 权限接口
 * 
 * @author brain
 *
 */
public class SecurityInterface {

	/** 地址 */
	private String url;
	/** 请求方式 */
	private Integer method;

	/**
	 * 构造方法
	 * 
	 * @param url    接口地址
	 * @param method 请求方式
	 */
	public SecurityInterface(String url, Integer method) {
		this.url = url;
		this.method = method;
	}

	/**
	 * 获取接口地址
	 * 
	 * @return 接口地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置接口地址
	 * 
	 * @param url 要设置的接口地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取请求方式
	 * 
	 * @return 请求方式
	 */
	public Integer getMethod() {
		return method;
	}

	/**
	 * 要设置的请求方式
	 * 
	 * @param method 要设置的请求方式
	 */
	public void setMethod(Integer method) {
		this.method = method;
	}

}
