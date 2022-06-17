package chiya.web.request.http;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import chiya.core.base.function.Function;
import chiya.web.request.space.ClientSpace;

/**
 * 创建请求工具
 * 
 * @author chiya
 */
public class RequestUtil {

	/**
	 * 获取一个请求
	 * 
	 * @return HttpClient
	 */
	public static HttpClient getRequest() {
		return ClientSpace.getRequest();
	}

	/**
	 * 发送请求，并处理成JSON格式
	 * 
	 * @param httpClient  发送的客户端
	 * @param requestBase 请求数据
	 * @return 响应数据
	 */
	public static String httpRquest(HttpClient httpClient, HttpUriRequest requestBase) {
		try {
			return EntityUtils.toString(httpClient.execute(requestBase).getEntity());
		} catch (IOException e) {
			new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 发送请求，并处理成JSON格式
	 * 
	 * @param httpClient  发送的客户端
	 * @param requestBase 请求数据
	 * @param error       错误失败回调
	 * @return 响应数据
	 */
	public static String httpRquest(HttpClient httpClient, HttpUriRequest requestBase, Function error) {
		try {
			return EntityUtils.toString(httpClient.execute(requestBase).getEntity());
		} catch (IOException e) {
			error.task();
		}
		return null;
	}

	/**
	 * 发送请求，并处理成JSON格式
	 * 
	 * @param httpClient  发送的客户端
	 * @param requestBase 请求数据
	 * @return 响应数据
	 */
	public static String send(HttpClient httpClient, HttpUriRequest requestBase) {
		if (ClientSpace.getAllHead() != null) {
			// 请求头处理
			ClientSpace.getAllHead().forEach((k, v) -> requestBase.addHeader(k, v));
		}
		return httpRquest(httpClient, requestBase);
	}

	/**
	 * get方式请求数据
	 * 
	 * @param url URL地址
	 * @return 响应数据
	 */
	public static String get(String url) {
		return send(getRequest(), HttpMethod.get(url));
	}

	/**
	 * get方式请求数据
	 * 
	 * @param url    URL地址
	 * @param params 参数
	 * @return 响应数据
	 */
	public static String get(String url, Object... params) {
		return send(getRequest(), HttpMethod.get(url, params));
	}

	/**
	 * Post方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param object 参数
	 * @return 响应数据
	 */
	public static String post(String url, Object object) {
		HttpPost httpPost = HttpMethod.postJson(url);
		if (object != null) { httpPost.setEntity(new StringEntity(JSONObject.toJSONString(object), "UTF-8")); }
		return send(getRequest(), httpPost);
	}

	/**
	 * put方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param object 参数
	 * @return 响应数据
	 */
	public static String put(String url, Object object) {
		HttpPut httpPut = HttpMethod.putJson(url);
		if (object != null) { httpPut.setEntity(new StringEntity(JSONObject.toJSONString(object), "UTF-8")); }
		return send(getRequest(), httpPut);
	}

	/**
	 * delete方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param params 参数
	 * @return 响应数据
	 */
	public static String delete(String url, Object... params) {
		return send(getRequest(), HttpMethod.delete(url, params));
	}

}
