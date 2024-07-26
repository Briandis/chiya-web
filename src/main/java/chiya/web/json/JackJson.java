package chiya.web.json;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JackJson {
	/** JSON构建对象 */
	private static ThreadLocal<ObjectMapper> threadLocal = ThreadLocal.withInitial(() -> new ObjectMapper());

	/**
	 * 转换对象
	 * 
	 * @param <T>     转换对象类型
	 * @param json    json字符串
	 * @param classer 类信息
	 * @return 对象
	 */
	public static <T> T parser(String json, Class<T> classer) {
		ObjectMapper objectMapper = threadLocal.get();
		try {
			return objectMapper.readValue(json, classer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转换对象
	 * 
	 * @param <T>     转换对象类型
	 * @param json    json字符串
	 * @param classer 类信息
	 * @return 对象
	 */
	public static <T> List<T> parserList(String json, Class<T> classer) {
		ObjectMapper objectMapper = threadLocal.get();
		try {
			return objectMapper.readValue(json, new TypeReference<List<T>>() {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 序列化
	 * 
	 * @param object 对象
	 * @return 序列化后字符串
	 */
	public static String toJson(Object object) {
		if (object == null) { return null; }
		ObjectMapper objectMapper = threadLocal.get();
		try {
			return object instanceof String ? (String) object : objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
