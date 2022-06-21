package chiya.web.security.function;

/**
 * 
 * @author brain
 *
 */
@FunctionalInterface
public interface MethodGroupInfo {

	/**
	 * 获取下一个接口的信息
	 * 
	 * @param method  请求方式
	 * @param groupId[] 组id的数组
	 */
	void next(String method, int groupId[]);
}
