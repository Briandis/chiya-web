package chiya.web.server.rpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

import chiya.core.base.object.ObjectUtil;
import chiya.server.ChiyaRpcPack;
import chiya.server.annotation.ChiyaRPC;
import chiya.web.base.type.TypeConversion;

/**
 * RPC实例对象
 * 
 * @author chiya
 *
 */
public class RpcMethod {

	/** 实例 */
	private Object instance;
	/** 引用的方法 */
	private ConcurrentHashMap<String, Method> refMethod = new ConcurrentHashMap<>();

	/**
	 * 构造方法
	 * 
	 * @param instance 实例对象
	 */
	public RpcMethod(Object instance) {
		this.instance = instance;
		init();
	}

	/**
	 * 构造方法
	 * 
	 * @param classer 类文件
	 */
	public RpcMethod(Class<?> classer) {
		this.instance = ObjectUtil.newObject(classer);
		init();
	}

	/** 初始化 */
	private void init() {
		for (Method method : instance.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(ChiyaRPC.class)) {
				ChiyaRPC chiyaRPC = method.getAnnotation(ChiyaRPC.class);
				refMethod.put(chiyaRPC.value(), method);
			}
		}
	}

	/**
	 * 注册
	 * 
	 * @param map 映射
	 */
	public void register(ConcurrentHashMap<String, RpcMethod> map) {
		refMethod.forEach((url, method) -> map.put(url, this));
	}

	/**
	 * 执行
	 * 
	 * @param url     url
	 * @param rpcPack RPC结构包
	 * @return 执行结果
	 */
	public Object execute(String url, ChiyaRpcPack rpcPack) {
		if (refMethod.containsKey(url)) {
			Method method = refMethod.get(url);
			try {
				if (rpcPack.getChiyaRpcHead().getJson()) {
					JSONObject json = JSONObject.parseObject(new String(rpcPack.getData()));
					Parameter[] params = method.getParameters();
					Type[] genericParameterTypes = method.getGenericParameterTypes();
					Object[] args = new Object[params.length];
					for (int i = 0; i < params.length; i++) {
						Object obj = TypeConversion.handleObject(params[i], json, genericParameterTypes[i]);
						args[i] = obj;
					}
					method.setAccessible(true);
					return method.invoke(instance, args);
				}
				Parameter[] params = method.getParameters();
				Object[] args = new Object[params.length];
				for (int i = 0; i < params.length; i++) {
					args[i] = null;
				}
				args[0] = new String(rpcPack.getData());
				method.setAccessible(true);
				return method.invoke(instance, args);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

}
