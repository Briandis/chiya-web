package chiya.web.excel;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.function.Function;

import chiya.core.base.time.DateUtil;

/**
 * Excel基础配置类模板
 * 
 * @author chiya
 * @param <T> 格式化的实体类
 */
public abstract class BaseFormatter<T> {
	/** 配置内容 */
	public final LinkedHashMap<String, Function<T, Object>> fieldConfig = new LinkedHashMap<String, Function<T, Object>>();
	/** 格式化方法内容 */
	public final LinkedHashMap<String, Function<Object, String>> formatterConfig = new LinkedHashMap<String, Function<Object, String>>();

	/**
	 * 添加
	 * 
	 * @param name 字段名称
	 * @param get  从对象中获取的方法
	 */
	public final void put(String name, Function<T, Object> get) {
		fieldConfig.put(name, get);
	}

	/**
	 * 添加
	 * 
	 * @param name 字段名称
	 * @param get  从对象中获取的方法
	 * @param set  格式化的方法
	 */
	public final void put(String name, Function<T, Object> get, Function<Object, String> set) {
		fieldConfig.put(name, get);
		formatterConfig.put(name, set);
	}

	/**
	 * 格式化对象
	 * 
	 * @param key  键
	 * @param data 格式化的数据
	 * @return 字符串
	 */
	public final String formatter(String key, Object data) {
		if (data == null) { return null; }
		if (formatterConfig.containsKey(key)) { return formatterConfig.get(key).apply(data); }
		return data.toString();
	}

	/**
	 * 日期格式化
	 * 
	 * @param object 格式化对象
	 * @return 格式化后字符串
	 */
	public static String date(Object object) {
		if (object != null && object instanceof Date) { return DateUtil.formatDateYYYYMMDDHHMMSS((Date) object); }
		return null;

	}

}