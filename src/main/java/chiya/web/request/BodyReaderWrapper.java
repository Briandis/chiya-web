package chiya.web.request;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 将JSON参数获取并封装成一个的KEY-VALUE结构，并让inputStram可重复读。
 */
public class BodyReaderWrapper extends HttpServletRequestWrapper {
	/** 备份的请求体 */
	private final byte[] body;
	/** 参数 */
	private Map<String, String[]> params = new HashMap<>();
	/** JSON类型 */
	private static final String TYPE = "application/json";

	/**
	 * 构造方法
	 * 
	 * @param request 请求
	 */
	public BodyReaderWrapper(HttpServletRequest request) throws IOException {
		super(request);
		String sessionStream = getBodyString(request);
		// 备份
		body = sessionStream.getBytes(Charset.forName("UTF-8"));
		// 参数转化
		params.putAll(request.getParameterMap());
		// 是JSON的就转成普通的key-value的方式
		if (TYPE.equals(request.getContentType())) { addAllParameters(JSONObject.parseObject(sessionStream)); }
	}

	/**
	 * 获取请求体中的字符串
	 * 
	 * @param request 请求对象
	 * @return 请求体中的字符串
	 */
	private String getBodyString(ServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) { stringBuilder.append(line); }
			bufferedReader.close();
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return stringBuilder.toString();
	}

	/**
	 * 添加新的参数
	 * 
	 * @param name  名称
	 * @param value 值
	 */
	public void addParameter(String name, Object value) {

		if (value != null) {
			if (value instanceof String[]) {
				params.put(name, (String[]) value);
			} else if (value instanceof String) {
				params.put(name, new String[] { (String) value });
			} else if (value instanceof JSONArray) {
				JSONArray data = (JSONArray) value;
				String v[] = new String[data.size()];
				for (int i = 0; i < v.length; i++) { v[i] = data.getString(i); }
				params.put(name, v);
			} else {
				params.put(name, new String[] { String.valueOf(value) });
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public Enumeration<String> getParameterNames() {
		return new Vector<String>(params.keySet()).elements();
	}

	/**
	 * 添加新的参数
	 * 
	 * @param map 新的参数
	 */
	public void addAllParameters(Map<String, Object> map) {
		if (map != null) { map.forEach((key, value) -> { addParameter(key, value); }); }

	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return params;
	}

	@Override
	public String getParameter(String name) {
		String[] values = params.get(name);
		if (values == null || values.length == 0) { return null; }
		return values[0];
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = params.get(name);
		if (values == null || values.length == 0) { return null; }
		return values;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return bais.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {}
		};
	}
}