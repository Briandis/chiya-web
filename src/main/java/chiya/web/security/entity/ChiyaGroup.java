package chiya.web.security.entity;

/**
 * 权限群组
 * 
 * @author brain
 *
 */
public class ChiyaGroup {

	/** 访客 */
	public static final int PUBLIC = 0;
	/** 普通用户 */
	public static final int USER = 1;
	/** 后台人员 */
	public static final int ADMIN = 2;
	/** 对外接口 */
	public static final int SERVER = 3;
	/** 角色容器 */
	private static SecurityRole arrayRole[];

	/**
	 * 获取缓存的角色列表
	 * 
	 * @return 角色列表
	 */
	public static SecurityRole[] getRole() {
		if (arrayRole == null) { arrayRole = newRoleArray(); }
		return arrayRole;
	}

	/**
	 * 构建默认的角色列表
	 * 
	 * @return 角色列表
	 */
	public static SecurityRole[] newRoleArray() {
		SecurityRole array[] = {
			new SecurityRole(USER, "前台", "前台用户才可以访问"),
			new SecurityRole(ADMIN, "后台", "后台用户才可以访问"),
			new SecurityRole(SERVER, "服务", "服务和服务间调用"),
		};
		return array;
	}

	/**
	 * 构建默认的角色列表
	 * 
	 * @return 角色列表
	 */
	public static SecurityRole[] refreshRoleArray() {
		arrayRole = newRoleArray();
		return arrayRole;
	}

	/**
	 * 带有游客信息的全部角色数据
	 * 
	 * @return 角色列表
	 */
	public static SecurityRole[] newAllRoleArray() {
		SecurityRole array[] = newRoleArray();
		SecurityRole newArray[] = new SecurityRole[array.length + 1];
		newArray[0] = new SecurityRole(PUBLIC, "公开", "无限制访问");
		for (int i = 1; i < newArray.length; i++) {
			newArray[i] = array[i - 1];
		}
		return newArray;
	}
}
