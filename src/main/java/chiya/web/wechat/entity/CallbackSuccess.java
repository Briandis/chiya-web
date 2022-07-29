package chiya.web.wechat.entity;

/**
 * 通知回调成功信息
 */
public class CallbackSuccess {
	public String code = "SUCCESS";
	public String message = "成功";

	public static CallbackSuccess get() {
		return new CallbackSuccess();
	}
}
