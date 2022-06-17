package chiya.web.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import chiya.core.base.collection.ChiyaHashMapValueMap;
import chiya.web.security.entity.ChiyaRole;
import chiya.web.security.entity.SecurityInterface;
import chiya.web.security.entity.SecurityRole;

/**
 * 权限认证<br>
 * 角色和接口绑定的，在代码中已经控制，所以只需要加载。
 * 
 * @author brain
 *
 */
public class SecurityCertification {

	/** 角色和接口的存储容器 */
	public static ChiyaHashMapValueMap<Integer, String, Integer> chiyaHashMapValueMap;

	/**
	 * 检查该用户访问的地址是否在配置中
	 * 
	 * @param url    访问的地址
	 * @param method 请求方式
	 * @param roleId 持有的角色id
	 * @return true:通过/false:失败
	 */
	public static boolean check(String url, int method, Collection<Integer> roleId) {
		if (roleId == null) { return false; }
		for (Integer role : roleId) {
			Integer value = chiyaHashMapValueMap.get(role, url);
			if (value != null) { if ((method & value) == method) { return true; } }
		}
		return false;
	}

	/**
	 * 检查是否是游客接口
	 * 
	 * @return true:通过/false:失败
	 */
	public static boolean checkTourists(String url, int method) {
		Integer value = chiyaHashMapValueMap.get(0, url);
		return value != null && (method & value) == method;
	}

	/**
	 * 获取所有的权限数据
	 * 
	 * @param securityRoles 角色列表
	 * @return List<SecurityRole> 角色持有的全部信息
	 */
	public static List<SecurityRole> listSecurity(SecurityRole[] securityRoles) {
		List<SecurityRole> list = Arrays.asList(securityRoles);
		for (SecurityRole securityRole : list) {
			if (chiyaHashMapValueMap.get(securityRole.getId()) != null) {
				securityRole.setListInterface(new ArrayList<>());
				chiyaHashMapValueMap.get(securityRole.getId()).forEach(
					(k, v) -> securityRole.getListInterface().add(new SecurityInterface(k, v))
				);
			}
		}
		return list;
	}

	/**
	 * 获取所有的权限数据
	 * 
	 * @return List<SecurityRole> 角色持有的全部信息
	 */
	public static List<SecurityRole> listSecurity() {
		List<SecurityRole> list = Arrays.asList(ChiyaRole.newAllRoleArray());
		for (SecurityRole securityRole : list) {
			if (chiyaHashMapValueMap.get(securityRole.getId()) != null) {
				securityRole.setListInterface(new ArrayList<>());
				chiyaHashMapValueMap.get(securityRole.getId()).forEach(
					(k, v) -> securityRole.getListInterface().add(new SecurityInterface(k, v))
				);
			}
		}
		return list;
	}

}
