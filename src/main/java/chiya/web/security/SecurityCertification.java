package chiya.web.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.collection.ChiyaMap;
import chiya.web.security.entity.ChiyaRole;
import chiya.web.security.entity.InterfaceGroup;
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
	public static ChiyaMap<Integer, String, Integer> chiyaHashMapValueMap;
	/** 接口和接口组的存储容器 */
	public static ConcurrentHashMap<String, InterfaceGroup> chiyaGroup;

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
	 * 检查该接口是否属于这个组
	 * 
	 * @param url     访问的地址
	 * @param method  请求方式
	 * @param groupId 组id
	 * @return true:通过/false:失败
	 */
	public static boolean checkGroup(String url, String method, Collection<Integer> groupId) {
		if (groupId == null) { return false; }
		for (Integer group : groupId) {
			if (checkGroup(url, method, group)) { return true; } ;
		}
		return false;
	}

	/**
	 * 检查该接口是否属于这个组
	 * 
	 * @param url     访问的地址
	 * @param method  请求方式
	 * @param groupId 组id
	 * @return true:通过/false:失败
	 */
	public static boolean checkGroup(String url, String method, int groupId) {
		if (chiyaGroup.containsKey(url)) { return chiyaGroup.get(url).check(method, groupId); }
		return false;
	}

	/**
	 * 获取接口分组
	 * 
	 * @param url    接口的地址
	 * @param method 请求方式
	 * @return 该接口生效的分组
	 */
	public static int[] getGroup(String url, String method) {
		if (chiyaGroup.containsKey(url)) { return chiyaGroup.get(url).getMethod(method).valueToIntArray(); }
		return null;
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
