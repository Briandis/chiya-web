package chiya.web.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户个人信息原始对象
 * 
 * @author chiya
 */
@Getter
@Setter
public class UserProfile {
	/** 用户信息对象 */
	@Getter
	@Setter
	public class UserInfo {
		/** 昵称 */
		private String nickName;
		/** 头像地址 */
		private String avatarUrl;
	}

	/** 不包括敏感信息的原始数据字符串，用于计算签名 */
	private String rawData;
	/** 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息 */
	private String signature;
	/** 包括敏感数据在内的完整用户信息的加密数据 */
	private String encryptedData;
	/** 加密算法的初始向量 */
	private String iv;
	/** 敏感数据对应的云 ID，开通云开发的小程序才会返回 */
	private String cloudID;
}
