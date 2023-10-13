package chiya.web.security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import chiya.core.base.collection.ChiyaHashMapValueMap;
import chiya.core.base.collection.ContainerUtil;
import chiya.security.Method;
import chiya.security.annotation.ChiyaSecurity;
import chiya.security.annotation.ChiyaSecurityGroup;
import chiya.web.security.entity.InterfaceGroup;

/**
 * 获取全部接口工具
 */
public class ChiyaSecurityExtract {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	/** 自定义系统路径前缀 */
	public static String servicePath = "";

	/**
	 * 获取注册的权限信息
	 * 
	 * @return ChiyaHashMapValueMap<Integer, String, Integer> 角色-地址-请求方式
	 */
	public ChiyaHashMapValueMap<Integer, String, Integer> getURL() {
		ChiyaHashMapValueMap<Integer, String, Integer> chiyaHashMapValueMap = new ChiyaHashMapValueMap<>();
		int[] zero = { 0 };
		// 迭代全部注册的接口
		requestMappingHandlerMapping.getHandlerMethods().forEach((k, v) -> {
			int value[] = null;
			if (v.getMethodAnnotation(ChiyaSecurity.class) != null) { value = v.getMethodAnnotation(ChiyaSecurity.class).value(); }
			// 注册的接口
			Set<String> pathSet = k.getDirectPaths();
			// 注册的请求方式
			Set<RequestMethod> methodSet = k.getMethodsCondition().getMethods();
			int method = 0;
			// 没有请求方式则支持全部
			if (methodSet == null || methodSet.size() == 0) { method = 255; }
			for (RequestMethod requestMethod : methodSet) {
				method += Method.getByte(requestMethod.name());

			}
			for (String path : pathSet) {
				if (value == null) { value = zero; }
				for (int role : value) {
					Integer data = chiyaHashMapValueMap.get(role, servicePath + path);
					if (data != null) { method = data + method; }
					chiyaHashMapValueMap.put(role, servicePath + path, method);
				}
			}
		});
		return chiyaHashMapValueMap;
	}

	/**
	 * 获取注册的权限信息
	 * 
	 * @return ConcurrentHashMap<String, InterfaceGroup> 地址-接口信息
	 */
	public ConcurrentHashMap<String, InterfaceGroup> getGroup() {
		ConcurrentHashMap<String, InterfaceGroup> concurrentHashMap = new ConcurrentHashMap<String, InterfaceGroup>();
		int[] zero = { 0 };
		// 迭代全部注册的接口
		requestMappingHandlerMapping.getHandlerMethods().forEach((k, v) -> {
			int value[] = null;
			if (v.getMethodAnnotation(ChiyaSecurityGroup.class) != null) { value = v.getMethodAnnotation(ChiyaSecurityGroup.class).value(); }
			// 注册的接口
			Set<String> pathSet = k.getDirectPaths();
			// 注册的请求方式
			Set<RequestMethod> methodSet = k.getMethodsCondition().getMethods();
			for (String path : pathSet) {
				if (value == null) { value = zero; }
				for (int groupId : value) {
					if (methodSet == null || methodSet.size() == 0) {
						// 存储全部类型
						for (int i = 0; i < 8; i++) {
							ContainerUtil.getValueOrPut(concurrentHashMap, servicePath + path, InterfaceGroup.class).setGroup(i, groupId);
						}
					}
					// 单个的情况
					for (RequestMethod requestMethod : methodSet) {
						ContainerUtil.getValueOrPut(concurrentHashMap, servicePath + path, InterfaceGroup.class).setGroup(requestMethod.name(), groupId);
					}
				}
			}
		});
		return concurrentHashMap;
	}
}
