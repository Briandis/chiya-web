package chiya.web.request.http;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSONObject;

/**
 * HTTP常用请求处理
 * 
 * @author brain
 *
 */
public class HttpMethod {

	/**
	 * 生成对应的StringEntity
	 * 
	 * @param object 普通对象
	 * @return StringEntity
	 */
	public static StringEntity toStringEntity(Object object) {
		return new StringEntity(JSONObject.toJSONString(object), "UTF-8");
	}

	/**
	 * 创建get请求对象
	 * 
	 * @param url 路径
	 * @return HttpGet GET对象
	 */
	public static HttpGet get(String url) {
		return new HttpGet(url);
	}

	/**
	 * 创建get请求对象
	 * 
	 * @param url    访问的地址
	 * @param params 请求对象参数
	 * @return HttpGet
	 */
	public static HttpGet get(String url, Object... params) {
		return new HttpGet(url + Parameter.toRULParament(params));
	}

	/**
	 * 创建JSON传输的get请求对象
	 * 
	 * @param url 路径
	 * @return HttpGet GET对象
	 */
	public static HttpGet getJson(String url) {
		return setJsonRequest(get(url));
	}

	/**
	 * 创建JSON传输的get请求对象
	 * 
	 * @param url    路径
	 * @param params 请求对象参数
	 * @return HttpGet GET对象
	 */
	public static HttpGet getJson(String url, Object... params) {
		return setJsonRequest(get(url, params));
	}

	/**
	 * 创建JSON传输的POST请求
	 * 
	 * @param url 请求地址
	 * @return HttpPost对象
	 */
	public static HttpPost post(String url) {
		return new HttpPost(url);
	}

	/**
	 * 创建JSON传输的POST请求
	 * 
	 * @param url  请求地址
	 * @param data 传输的数据
	 * @return HttpPost对象
	 */
	public static HttpPost post(String url, String data) {
		HttpPost httpPost = post(url);
		if (data != null) { httpPost.setEntity(new StringEntity(data, "UTF-8")); }
		return httpPost;
	}

	/**
	 * 创建JSON传输的POST请求
	 * 
	 * @param url 请求地址
	 * @return HttpPost对象
	 */
	public static HttpPost postJson(String url) {
		return setJsonRequest(post(url));
	}

	/**
	 * 创建JSON传输的POST请求
	 * 
	 * @param url  请求地址
	 * @param data 传输的数据
	 * @return HttpPost对象
	 */
	public static HttpPost postJson(String url, String data) {
		HttpPost httpPost = postJson(url);
		if (data != null) { httpPost.setEntity(new StringEntity(data, "UTF-8")); }
		return httpPost;
	}

	/**
	 * 创建JSON传输的POST请求
	 * 
	 * @param url  请求地址
	 * @param data 传输的数据
	 * @return HttpPost对象
	 */
	public static HttpPost postJson(String url, Object data) {
		HttpPost httpPost = postJson(url);
		if (data != null) { httpPost.setEntity(toStringEntity(data)); }
		return httpPost;
	}

	/**
	 * 创建PUT的JSON请求对象
	 * 
	 * @param url 请求路径
	 * @return HttpPut对象
	 */
	public static HttpPut putJson(String url) {
		return setJsonRequest(new HttpPut(url));
	}

	/**
	 * 创建PUT的JSON请求对象
	 * 
	 * @param url  请求路径
	 * @param data 传输的数据
	 * @return HttpPut对象
	 */
	public static HttpPut putJson(String url, String data) {
		HttpPut httpPut = putJson(url);
		if (data != null) { httpPut.setEntity(new StringEntity(data, "UTF-8")); }
		return httpPut;
	}

	/**
	 * 创建PUT的JSON请求对象
	 * 
	 * @param url  请求路径
	 * @param data 传输的数据
	 * @return HttpPut对象
	 */
	public static HttpPut putJson(String url, Object data) {
		HttpPut httpPut = putJson(url);
		if (data != null) { httpPut.setEntity(toStringEntity(data)); }
		return httpPut;
	}

	/**
	 * 创建DELETE请求对象
	 * 
	 * @param url 请求地址
	 * @return HttpDelete
	 */
	public static HttpDelete delete(String url) {
		return new HttpDelete(url);
	}

	/**
	 * 创建DELETE请求对象
	 * 
	 * @param url    请求地址
	 * @param params 请求参数
	 * @return HttpDelete
	 */
	public static HttpDelete delete(String url, Object... params) {
		return new HttpDelete(url + Parameter.toRULParament(params));
	}

	/**
	 * 设置请求头为JSON格式
	 * 
	 * @param <T>         请求泛型
	 * @param requestBase 请求对象
	 * @return 请求对象
	 */
	public static <T extends HttpUriRequest> T setJsonRequest(T requestBase) {
		if (requestBase != null) {
			requestBase.addHeader("Accept", "application/json");
			requestBase.addHeader("Content-type", "application/json;charset=utf-8");
		}
		return requestBase;
	}
}
