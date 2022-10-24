package chiya.web.request.space;

import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import chiya.core.base.thread.ChiyaSpace;

/**
 * 发送请求所需要的线程存储空间
 * 
 * @author chiya
 *
 */
public class RequestSpace {
	/** 私有化构造方法 */
	private RequestSpace() {}

	/** 存储容器 */
	private static final ChiyaSpace<String, Object> CHIYA_SPACE = new ChiyaSpace<>();

	/** 线程空间的名称 */
	private static final String HTTP_CLIENT = "httpClient";
	/** 请求头参数 */
	private static final String HEAD = "head";

	/**
	 * 获取对应线程的链接
	 * 
	 * @return HTTP链接
	 */
	public static HttpClient getRequest() {
		return toHttpClient(
			CHIYA_SPACE.computeIfAbsent(
				HTTP_CLIENT,
				HttpClientBuilder.create().build()
			)
		);
	}

	/**
	 * 转换成HTTP链接
	 * 
	 * @param object 对象
	 * @return HttpClient
	 */
	private static HttpClient toHttpClient(Object object) {
		if (object instanceof HttpClient) { return (HttpClient) object; }
		return null;
	}

	/**
	 * 转换成HashMap
	 * 
	 * @param object 对象
	 * @return HashMap<String, String>
	 */
	@SuppressWarnings("unchecked")
	private static HashMap<String, String> toHashMap(Object object) {
		if (object instanceof HashMap) { return (HashMap<String, String>) object; }
		return null;
	}

	/**
	 * 设置请求头内容
	 * 
	 * @param head  请求头
	 * @param value 内容
	 */
	public static void setHead(String head, String value) {
		toHashMap(
			CHIYA_SPACE.computeIfAbsent(HEAD, new HashMap<String, String>())
		).put(head, value);
	}

	/**
	 * 删除请求头
	 * 
	 * @param head 请求头
	 */
	public static void removeHead(String head) {
		if (CHIYA_SPACE.get(HEAD) != null) { toHashMap(CHIYA_SPACE.get(HEAD)).remove(head); }
	}

	/**
	 * 清除全部请求头
	 */
	public static void removeAllHead() {
		if (CHIYA_SPACE.get(HEAD) != null) { toHashMap(CHIYA_SPACE.get(HEAD)).clear(); }
	}

	/**
	 * 获取全部请求头
	 * 
	 * @return 请求头信息
	 */
	public static HashMap<String, String> getAllHead() {
		return toHashMap(CHIYA_SPACE.get(HEAD));
	}

}
