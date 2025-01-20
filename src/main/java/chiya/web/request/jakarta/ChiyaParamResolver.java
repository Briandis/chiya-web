package chiya.web.request.jakarta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSONObject;

import chiya.core.base.number.NumberUtil;
import chiya.core.base.object.ObjectUtil;
import chiya.core.base.time.DateUtil;
import chiya.server.annotation.ChiyaParam;

/**
 * 自定义参数转换
 * 
 * @author chiya
 *
 */
public class ChiyaParamResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 只有list和标记对象的会处理
		// 判断注解
		if (parameter.hasParameterAnnotation(ChiyaParam.class)) { return true; }
		// 判断列表
		if (List.class.isAssignableFrom(parameter.getParameterType())) { return true; }
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Class<?> praramType = parameter.getParameterType();
		String data = webRequest.getParameter(parameter.getParameterName());
		// 基础类型，防止注解滥用
		if (Integer.class.equals(praramType)) { return NumberUtil.parseInt(data); }
		if (Long.class.equals(praramType)) { return NumberUtil.parseLong(data); }
		if (Float.class.equals(praramType)) { return NumberUtil.parseFloat(data); }
		if (Double.class.equals(praramType)) { return NumberUtil.parseDouble(data); }
		if (Date.class.equals(praramType)) { return DateUtil.stringToDate(data); }
		if (String.class.equals(praramType)) { return data; }

		// List处理
		if (List.class.isAssignableFrom(praramType)) {
			String[] values = webRequest.getParameterValues(parameter.getParameterName());
			Class<?> classer = ObjectUtil.getGenericTypeFirst(parameter.getGenericParameterType());
			List<Object> list = new ArrayList<>();
			if (values != null) {
				for (String jsonString : values) {
					if (JSONObject.isValid(jsonString)) {
						list.add(JSONObject.parseObject(jsonString, classer));
					} else {
						if (Integer.class.equals(classer)) {
							list.add(NumberUtil.parseInt(jsonString));
						} else if (Long.class.equals(classer)) {
							list.add(NumberUtil.parseLong(jsonString));
						} else if (Float.class.equals(classer)) {
							list.add(NumberUtil.parseFloat(jsonString));
						} else if (Double.class.equals(classer)) {
							list.add(NumberUtil.parseDouble(jsonString));
						} else if (Date.class.equals(classer)) {
							list.add(DateUtil.stringToDate(jsonString));
						} else {
							list.add(jsonString);
						}

					}
				}
			}
			return list;
		}
		// 通用对象处理
		return JSONObject.parseObject(data, praramType);
	}

}
