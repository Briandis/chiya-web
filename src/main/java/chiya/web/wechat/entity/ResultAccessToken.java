package chiya.web.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * AccessToken的回调返回对象
 * 
 * @author chiya
 */
@Setter
@Getter
public class ResultAccessToken {
	/** 获取到的凭证 */
	private String access_token;
	/** 凭证有效时间，单位：秒。目前是7200秒之内的值。 */
	private Integer expires_in;
	/** 错误码 */
	private Integer errcode;
	/** 错误信息 */
	private String errmsg;

}
