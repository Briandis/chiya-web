package chiya.web.security.entity;

import chiya.core.base.bit.BitMap;
import chiya.core.base.thread.ThreadUtil;
import chiya.security.Method;
import chiya.web.security.function.MethodGroupInfo;

/**
 * 接口组信息
 * 
 * @author brain
 *
 */
public class InterfaceGroup {
	/** 请求方式的组 */
	private BitMap methodGroup[] = new BitMap[8];

	/**
	 * 请求方式
	 * 
	 * @param method 请求方式
	 * @return 对应的位图
	 */
	public BitMap getMethod(String method) {
		int offset = Method.getOffset(method);
		if (offset < 0 || offset > 7) { return null; }
		return methodGroup[offset];
	}

	/**
	 * 请求方式
	 * 
	 * @param method 请求方式
	 * @return 对应的位图
	 */
	public BitMap getMethod(int method) {
		if (method < 0 || method > 7) { return null; }
		return methodGroup[method];
	}

	/**
	 * 请求方式，如果没有，则创建
	 * 
	 * @param method 请求方式
	 * @return 对应的位图
	 */
	public BitMap getMethodAndNew(String method) {
		int offset = Method.getOffset(method);
		if (offset < 0 || offset > 7) { throw new IllegalArgumentException(method + " is an unknown request method"); }
		ThreadUtil.doubleCheckLock(
			() -> methodGroup[offset] == null,
			methodGroup,
			() -> methodGroup[offset] = new BitMap(1)
		);
		return methodGroup[offset];
	}

	/**
	 * 请求方式，如果没有，则创建
	 * 
	 * @param method 请求方式
	 * @return 对应的位图
	 */
	public BitMap getMethodAndNew(int method) {
		if (method < 0 || method > 7) { throw new ArrayIndexOutOfBoundsException("The subscript needs to be between 0-7"); }
		ThreadUtil.doubleCheckLock(
			() -> methodGroup[method] == null,
			methodGroup,
			() -> methodGroup[method] = new BitMap(1)
		);
		return methodGroup[method];
	}

	/**
	 * 设置对应的请求方式下包含的组
	 * 
	 * @param method  请求方式
	 * @param groupId 所属组
	 */
	public void setGroup(String method, int groupId) {
		getMethodAndNew(method).add(groupId);
	}

	/**
	 * 设置对应的请求方式下包含的组
	 * 
	 * @param method  请求方式
	 * @param groupId 所属组
	 */
	public void setGroup(int method, int groupId) {
		getMethodAndNew(method).add(groupId);
	}

	/**
	 * 检查对应的请求方法中，是否存在该组id
	 * 
	 * @param method  请求方式
	 * @param groupId 组id
	 * @return true:存在/false:不存在
	 */
	public boolean check(String method, int groupId) {
		BitMap bitMap = getMethod(method);
		return bitMap != null && bitMap.get(groupId);
	}

	/**
	 * 检查对应的请求方法中，是否存在该组id
	 * 
	 * @param method  请求方式
	 * @param groupId 组id
	 * @return true:存在/false:不存在
	 */
	public boolean check(int method, int groupId) {
		BitMap bitMap = getMethod(method);
		return bitMap != null && bitMap.get(groupId);
	}

	/**
	 * 移除对应请求方式中的组id
	 * 
	 * @param method  请求方式
	 * @param groupId 组id
	 */
	public void removeGroup(String method, int groupId) {
		BitMap bitMap = getMethod(method);
		if (bitMap != null) { bitMap.remove(groupId); }
	}

	/**
	 * 移除对应请求方式中的组id
	 * 
	 * @param method  请求方式
	 * @param groupId 组id
	 */
	public void removeGroup(int method, int groupId) {
		BitMap bitMap = getMethod(method);
		if (bitMap != null) { bitMap.remove(groupId); }
	}

	/**
	 * 迭代接口信息内的数据
	 * 
	 * @param methodGroupInfo 接口信息，需要函数式传入,(method,groupId)->function;
	 */
	public void forEach(MethodGroupInfo methodGroupInfo) {
		if (methodGroup != null) {
			for (int i = 0; i < methodGroup.length; i++) {
				if (methodGroup[i] != null) { methodGroupInfo.next(Method.getMethod(i), methodGroup[i].valueToIntArray()); }
			}
		}
	}

}
