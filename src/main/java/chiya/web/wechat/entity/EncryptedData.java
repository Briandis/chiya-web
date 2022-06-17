package chiya.web.wechat.entity;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息解密后数据
 * 
 * @author chiya
 */
@Getter
@Setter
public class EncryptedData {
	private String openId;
	private String nickName;
	private String gender;
	private String city;
	private String province;
	private String country;
	private String avatarUrl;
	private String unionId;
	private Watermark watermark;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
