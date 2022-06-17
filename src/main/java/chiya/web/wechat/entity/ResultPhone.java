package chiya.web.wechat.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPhone {
	/** 错误码 */
	private Integer errcode;
	/** 错误提示信息 */
	private String errmsg;
	/** 用户手机号信息 */
	private PhoneInfo phone_info;

	@Getter
	@Setter
	public class PhoneInfo {
		/** 用户绑定的手机号（国外手机号会有区号） */
		private String phoneNumber;
		/** 没有区号的手机号 */
		private String purePhoneNumber;
		/** 区号 */
		private String countryCode;
		/** 数据水印 */
		private Watermark watermark;
	}
}
