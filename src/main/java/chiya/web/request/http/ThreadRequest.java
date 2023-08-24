package chiya.web.request.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import chiya.core.base.function.VoidGenericFunction;
import chiya.web.request.space.RequestSpace;

public class ThreadRequest {
	/**
	 * 获取一个请求
	 * 
	 * @return HttpClient
	 */
	public static HttpClient getRequest() {
		return RequestSpace.getRequest();
	}

	/**
	 * 发送请求，并处理成JSON格式
	 * 
	 * @param requestBase 请求数据
	 * @return 响应数据
	 */
	public static String httpRequest(HttpUriRequest requestBase) {
		return httpRequest(getRequest(), requestBase);
	}

	/**
	 * 发送请求，并处理成JSON格式
	 * 
	 * @param httpClient  发送的客户端
	 * @param requestBase 请求数据
	 * @return 响应数据
	 */
	public static String httpRequest(HttpClient httpClient, HttpUriRequest requestBase) {
		return httpRequest(
			httpClient,
			requestBase,
			e -> {
				throw new RuntimeException(e);
			}
		);
	}

	/**
	 * 发送请求，并处理成JSON格式
	 * 
	 * @param httpClient  发送的客户端
	 * @param requestBase 请求数据
	 * @param error       错误失败回调
	 * @return 响应数据
	 */
	public static String httpRequest(HttpClient httpClient, HttpUriRequest requestBase, VoidGenericFunction<IOException> error) {
		try {
			HttpResponse httpResponse = httpClient.execute(requestBase);

			return EntityUtils.toString(httpResponse.getEntity());
		} catch (IOException e) {
			error.execute(e);
		}
		return null;
	}

	/**
	 * 发送请求，并处理成JSON格式<br>
	 * 会处理RequestSpace中设置的请求头
	 * 
	 * @param httpClient  发送的客户端
	 * @param requestBase 请求数据
	 * @return 响应数据
	 */
	public static String send(HttpClient httpClient, HttpUriRequest requestBase) {
		if (RequestSpace.getAllHead() != null) {
			// 请求头处理
			RequestSpace.getAllHead().forEach((k, v) -> requestBase.addHeader(k, v));
		}
		return httpRequest(httpClient, requestBase);
	}

	/**
	 * 发送请求，并处理成JSON格式<br>
	 * 会处理RequestSpace中设置的请求头
	 * 
	 * @param httpClient  发送的客户端
	 * @param requestBase 请求数据
	 * @return 响应数据
	 */
	public static String send(HttpUriRequest requestBase) {
		return send(getRequest(), requestBase);
	}
}
