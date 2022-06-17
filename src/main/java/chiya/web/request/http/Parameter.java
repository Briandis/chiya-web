package chiya.web.request.http;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数处理
 * 
 * @author brain
 *
 */
public class Parameter {

	/**
	 * 根据传入对象，生成对应方法
	 * 
	 * @param params 多个对象的属性
	 * @return URL拼接参数的字符串
	 */
	public static String toRULParament(Object... params) {
		StringBuilder stringBuilder = new StringBuilder();
		if (params != null) {
			for (Object object : params) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				// 生成所有属性对应的构造方法，包括父类
				getSuperField(hashMap, object.getClass());
				for (Method method : object.getClass().getMethods()) {
					// 名字相同且无参数
					if (hashMap.containsKey(method.getName()) && method.getParameterCount() == 0 && method.getReturnType() != null) {
						Object returnValue = null;
						try {
							returnValue = method.invoke(object);
						} catch (Exception e) {
							// 无处理
						}
						if (returnValue != null) {
							// 添加到参数中
							add(stringBuilder, hashMap.get(method.getName()), returnValue.toString());
						}
					}
				}
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 根据传入map生成URL的字符串
	 * 
	 * @param map 对象
	 * @return URL拼接字符串
	 */
	public static String toRULParament(Map<String, String> map) {
		StringBuilder stringBuilder = new StringBuilder();
		if (map != null) { map.forEach((key, value) -> add(stringBuilder, key, value)); }
		return stringBuilder.toString();
	}

	/**
	 * 对StringBuilder添加key和value
	 * 
	 * @param stringBuilder 字符串构造器
	 * @param key           键
	 * @param value         值
	 */
	public static void add(StringBuilder stringBuilder, String key, String value) {
		if (value != null && key != null) {
			// ?a=10&b=123的格式
			if (stringBuilder.length() == 0) { stringBuilder.append("?"); }
			// 只有首次没有&
			if (stringBuilder.length() > 1) { stringBuilder.append("&"); }
			stringBuilder.append(key).append("=").append(value);
		}
	}

	/**
	 * 根据字段名生成get方法名
	 * 
	 * @param fieldName 字段名
	 * @return get方法名
	 */
	public static String toGetMethod(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 递归查询该类型的所有属性
	 * 
	 * @param hashMap 存放容器
	 * @param clazz   查询的类型
	 */
	public static void getSuperField(HashMap<String, String> hashMap, Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			// key是get方法，value是属性
			hashMap.put(toGetMethod(field.getName()), field.getName());
		}
		if (clazz.getSuperclass() != null) {
			// 递归查询
			getSuperField(hashMap, clazz.getSuperclass());
		}
	}

}
