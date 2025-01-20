package chiya.web.base.type;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import chiya.core.base.number.NumberUtil;
import chiya.core.base.object.ObjectUtil;
import chiya.core.base.time.DateUtil;
import chiya.server.annotation.ChiyaRpcParam;

/**
 * 类型转换
 * 
 * @author chiya
 *
 */
public class TypeConversion {

	/**
	 * 处理类型转换
	 * 
	 * @param param                参数
	 * @param dataMap              数据
	 * @param genericParameterType 泛型类型
	 * @return 处理后的对象
	 */
	public static Object handle(Parameter param, Map<String, String> dataMap, Type genericParameterType) {

		Class<?> praramType = param.getType();
		String data = dataMap.get(param.getName());
		// 基础类型，防止注解滥用
		if (Integer.class.equals(praramType)) { return NumberUtil.parseInt(data); }
		if (Long.class.equals(praramType)) { return NumberUtil.parseLong(data); }
		if (Float.class.equals(praramType)) { return NumberUtil.parseFloat(data); }
		if (Double.class.equals(praramType)) { return NumberUtil.parseDouble(data); }
		if (Date.class.equals(praramType)) { return DateUtil.stringToDate(data); }
		if (String.class.equals(praramType)) { return data; }

		// List处理
		if (List.class.isAssignableFrom(praramType)) {
			Class<?> classer = ObjectUtil.getGenericTypeFirst(genericParameterType);
			return JSONObject.parseArray(data, classer);
		}
		// 通用对象处理
		return JSONObject.parseObject(data, praramType);
	}

	/**
	 * 处理类型转换
	 * 
	 * @param param                参数
	 * @param dataMap              数据
	 * @param genericParameterType 泛型类型
	 * @return 处理后的对象
	 */
	public static Object handleObject(Parameter param, Map<String, Object> dataMap, Type genericParameterType) {
		Class<?> praramType = param.getType();
		String data = null;
		String name = param.getName();
		ChiyaRpcParam rpcParam = param.getAnnotation(ChiyaRpcParam.class);
		if (rpcParam != null) { name = rpcParam.value(); }
		if (dataMap.containsKey(name)) {
			Object obj = dataMap.get(name);
			data = obj != null ? obj.toString() : null;
		}

		// 基础类型，防止注解滥用
		if (Integer.class.equals(praramType)) { return NumberUtil.parseInt(data); }
		if (Long.class.equals(praramType)) { return NumberUtil.parseLong(data); }
		if (Float.class.equals(praramType)) { return NumberUtil.parseFloat(data); }
		if (Double.class.equals(praramType)) { return NumberUtil.parseDouble(data); }
		if (Date.class.equals(praramType)) { return DateUtil.stringToDate(data); }
		if (String.class.equals(praramType)) { return data; }

		// List处理
		if (List.class.isAssignableFrom(praramType)) {
			Class<?> classer = ObjectUtil.getGenericTypeFirst(genericParameterType);
			return JSONObject.parseArray(data, classer);
		}
		// 通用对象处理
		return JSONObject.parseObject(data, praramType);
	}
}
