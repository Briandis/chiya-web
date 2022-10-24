package chiya.web.request.http;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 * 请求发送工具
 * 
 * @author chiya
 *
 */
public class ChiyaRequest {
	/** 请求头的数据 */
	private HashMap<String, String> requestHead = new HashMap<>();
	/** 响应的原始对象 */
	private HttpResponse httpResponse = null;
	/** 响应头数据 */
	private HashMap<String, String> responseHead = new HashMap<>();
	/** 响应体内容 */
	private String data = null;
	/** 获取HTTP链接 */
	private HttpClient httpClient = getHttpClient();

	/**
	 * 设置请求头的内容
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public void setHead(String key, String value) {
		requestHead.put(key, value);
	}

	/** 传输格式为JSON */
	public void contentTypeJSON() {
		requestHead.put("Accept", "application/json");
		requestHead.put("Content-type", "application/json;charset=utf-8");
	}

	/**
	 * 获取HTTP链接
	 * 
	 * @return HTTP链接
	 */
	private HttpClient getHttpClient() {
		try {
			return HttpClientBuilder.create()
				.setSSLSocketFactory(
					new SSLConnectionSocketFactory(
						// 忽略SSL证书验证
						SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
						NoopHostnameVerifier.INSTANCE
					)
				).build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 执行发送请求
	 * 
	 * @param requestBase 封装的请求数据
	 * @return 未处理请求体中的文本数据
	 */
	private String send(HttpUriRequest requestBase) {
		try {
			// 添加请求头
			requestHead.forEach((k, v) -> requestBase.addHeader(k, v));
			// 发送请求
			httpResponse = httpClient.execute(requestBase);
			// 处理响应头
			for (Header header : httpResponse.getAllHeaders()) {
				responseHead.put(header.getName(), header.getValue());
			}
			return data = EntityUtils.toString(httpResponse.getEntity());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取响应体数据
	 * 
	 * @return 响应体
	 */
	public String getData() {
		return data;
	}

	/**
	 * 获取响应头中的数据
	 * 
	 * @param head 响应头字段
	 * @return 获取的数据
	 */
	public String getResponseHead(String head) {
		return responseHead.get(head);
	}

	/**
	 * get方式请求数据
	 * 
	 * @param url URL地址
	 * @return 响应数据
	 */
	public String get(String url) {
		return send(HttpMethod.get(url));
	}

	/**
	 * get方式请求数据
	 * 
	 * @param url    URL地址
	 * @param params 参数
	 * @return 响应数据
	 */
	public String get(String url, Object... params) {
		return send(HttpMethod.get(url, params));
	}

	/**
	 * Post方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param object 参数
	 * @return 响应数据
	 */
	public String post(String url, Object object) {
		HttpPost httpPost = HttpMethod.postJson(url, object);
		return send(httpPost);
	}

	/**
	 * put方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param object 参数
	 * @return 响应数据
	 */
	public String put(String url, Object object) {
		HttpPut httpPut = HttpMethod.putJson(url, object);
		return send(httpPut);
	}

	/**
	 * delete方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param params 参数
	 * @return 响应数据
	 */
	public String delete(String url, Object... params) {
		return send(HttpMethod.delete(url, params));
	}

	/**
	 * 获取请求头的全部字段
	 * 
	 * @return 请求头的MAP
	 */
	public HashMap<String, String> getRequestHead() {
		return requestHead;
	}

	/**
	 * 获取原始的响应体
	 * 
	 * @return HttpResponse
	 */
	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	/**
	 * 获取全部的响应头
	 * 
	 * @return 响应头
	 */
	public HashMap<String, String> getResponseHead() {
		return responseHead;
	}

	/**
	 * 判断状态状态码是否相等
	 * 
	 * @param code 状态码
	 * @return true:相等/false:不相等
	 */
	public boolean isCode(int code) {
		if (httpResponse != null) { return httpResponse.getStatusLine().getStatusCode() == code; }
		return false;
	}

	/**
	 * 判断响应是否成功
	 * 
	 * @return true:成功/false:未成功
	 */
	public boolean isOK() {
		return isCode(200);
	}

}
