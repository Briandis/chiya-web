package chiya.web.request.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

/**
 * 创建请求工具
 * 
 * @author chiya
 */
public class RequestUtil {

	/**
	 * get方式请求数据
	 * 
	 * @param url URL地址
	 * @return 响应数据
	 */
	public static String get(String url) {
		return ThreadRequest.send(HttpMethod.get(url));
	}

	/**
	 * get方式请求数据
	 * 
	 * @param url    URL地址
	 * @param params 参数
	 * @return 响应数据
	 */
	public static String get(String url, Object... params) {
		return ThreadRequest.send(HttpMethod.get(url, params));
	}

	/**
	 * Post方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param object 参数
	 * @return 响应数据
	 */
	public static String post(String url, Object object) {
		HttpPost httpPost = HttpMethod.postJson(url, object);
		return ThreadRequest.send(httpPost);
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
		return ThreadRequest.send(httpPut);
	}

	/**
	 * delete方式发送请求数据
	 * 
	 * @param url    URL地址
	 * @param params 参数
	 * @return 响应数据
	 */
	public static String delete(String url, Object... params) {
		return ThreadRequest.send(HttpMethod.delete(url, params));
	}

}
