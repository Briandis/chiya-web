package chiya.web.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 手机号实体
 * 
 * @author chiya
 */
@Setter
@Getter
public class PhoneCodeEntity {
	private String code;

	public PhoneCodeEntity(String code) {
		this.code = code;
	}
}
