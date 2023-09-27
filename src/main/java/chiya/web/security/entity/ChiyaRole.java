package chiya.web.security.entity;

/**
 * 角色列表
 * 
 */
public class ChiyaRole {

	/** 访客 */
	public static final int PUBLIC = 0;
	/** 普通用户 */
	public static final int USER = 1;
	/** ROOT管理员 */
	public static final int ROOT = 2;
	/** 后台人员 */
	public static final int ADMIN = 3;
	/** 对外接口 */
	public static final int SERVER = 4;
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
			new SecurityRole(USER, "普通用户", "可以对自己账号信息编辑"),
			new SecurityRole(ROOT, "系统管理员", "最高权限"),
			new SecurityRole(ADMIN, "后台管理员", "可以查看后台公开内容"),
			new SecurityRole(SERVER, "对内服务接口", "服务和服务间调用"),
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
		newArray[0] = new SecurityRole(PUBLIC, "游客", "没有账号就可以访问");
		for (int i = 1; i < newArray.length; i++) {
			newArray[i] = array[i - 1];
		}
		return newArray;
	}
}
