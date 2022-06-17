package chiya.web.request.space;

import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import chiya.core.base.thread.ThreadUtil;

public class ClientSpace {

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

	/** 线程空间的名称 */
	private static final String HTTP_CLIENT = "httpClient";
	/** 请求头参数 */
	private static final String HEAD = "head";

	/**
	 * 设置请求头内容
	 * 
	 * @param head  请求头
	 * @param value 内容
	 */
	public static void setHead(String head, String value) {
		ThreadUtil.conditionLock(
			() -> ObjectSpace.get(HEAD) == null,
			ObjectSpace.class,
			() -> ObjectSpace.put(HEAD, new HashMap<>())
		);
		toHashMap(ObjectSpace.get(HEAD)).put(head, value);
	}

	/**
	 * 删除请求头
	 * 
	 * @param head 请求头
	 */
	public static void removeHead(String head) {
		if (ObjectSpace.get(HEAD) != null) { toHashMap(ObjectSpace.get(HEAD)).remove(head); }
	}

	/**
	 * 清楚全部请求头
	 */
	public static void removeAllHead() {
		if (ObjectSpace.get(HEAD) != null) { toHashMap(ObjectSpace.get(HEAD)).clear(); }
	}

	/**
	 * 获取全部请求头
	 * 
	 * @return 请求头信息
	 */
	public static HashMap<String, String> getAllHead() {
		return toHashMap(ObjectSpace.get(HEAD));
	}

	/**
	 * 获取对应线程的链接
	 * 
	 * @return HttpClient
	 */
	public static HttpClient getRequest() {
		ThreadUtil.conditionLock(
			() -> ObjectSpace.get(HTTP_CLIENT) == null,
			ObjectSpace.class,
			() -> ObjectSpace.put(HTTP_CLIENT, HttpClientBuilder.create().build())
		);
		return toHttpClient(ObjectSpace.get(HTTP_CLIENT));
	}
}
