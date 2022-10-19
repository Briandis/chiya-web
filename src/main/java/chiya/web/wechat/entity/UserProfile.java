package chiya.web.wechat.entity;

/**
 * 用户个人信息原始对象
 * 
 * @author chiya
 */
public class UserProfile {
	/** 用户信息对象 */
	public class UserInfo {
		/** 昵称 */
		private String nickName;
		/** 头像地址 */
		private String avatarUrl;

		/**
		 * 获取昵称
		 * 
		 * @return 昵称
		 */
		public String getNickName() {
			return nickName;
		}

		/**
		 * 设置昵称
		 * 
		 * @param nickName 昵称
		 */
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		/**
		 * 链式添加昵称
		 * 
		 * @param nickName 昵称
		 * @return 对象本身
		 */
		public UserInfo chainNickName(String nickName) {
			setNickName(nickName);
			return this;
		}

		/**
		 * 获取头像地址
		 * 
		 * @return 头像地址
		 */
		public String getAvatarUrl() {
			return avatarUrl;
		}

		/**
		 * 设置头像地址
		 * 
		 * @param avatarUrl 头像地址
		 */
		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}

		/**
		 * 链式添加头像地址
		 * 
		 * @param avatarUrl 头像地址
		 * @return 对象本身
		 */
		public UserInfo chainAvatarUrl(String avatarUrl) {
			setAvatarUrl(avatarUrl);
			return this;
		}

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

	/**
	 * 获取不包括敏感信息的原始数据字符串，用于计算签名
	 * 
	 * @return 不包括敏感信息的原始数据字符串，用于计算签名
	 */
	public String getRawData() {
		return rawData;
	}

	/**
	 * 设置不包括敏感信息的原始数据字符串，用于计算签名
	 * 
	 * @param rawData 不包括敏感信息的原始数据字符串，用于计算签名
	 */
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	/**
	 * 链式添加不包括敏感信息的原始数据字符串，用于计算签名
	 * 
	 * @param rawData 不包括敏感信息的原始数据字符串，用于计算签名
	 * @return 对象本身
	 */
	public UserProfile chainRawData(String rawData) {
		setRawData(rawData);
		return this;
	}

	/**
	 * 获取使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 * 
	 * @return 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 设置使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 * 
	 * @param signature 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * 链式添加使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 * 
	 * @param signature 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 * @return 对象本身
	 */
	public UserProfile chainSignature(String signature) {
		setSignature(signature);
		return this;
	}

	/**
	 * 获取包括敏感数据在内的完整用户信息的加密数据
	 * 
	 * @return 包括敏感数据在内的完整用户信息的加密数据
	 */
	public String getEncryptedData() {
		return encryptedData;
	}

	/**
	 * 设置包括敏感数据在内的完整用户信息的加密数据
	 * 
	 * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
	 */
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	/**
	 * 链式添加包括敏感数据在内的完整用户信息的加密数据
	 * 
	 * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
	 * @return 对象本身
	 */
	public UserProfile chainEncryptedData(String encryptedData) {
		setEncryptedData(encryptedData);
		return this;
	}

	/**
	 * 获取加密算法的初始向量
	 * 
	 * @return 加密算法的初始向量
	 */
	public String getIv() {
		return iv;
	}

	/**
	 * 设置加密算法的初始向量
	 * 
	 * @param iv 加密算法的初始向量
	 */
	public void setIv(String iv) {
		this.iv = iv;
	}

	/**
	 * 链式添加加密算法的初始向量
	 * 
	 * @param iv 加密算法的初始向量
	 * @return 对象本身
	 */
	public UserProfile chainIv(String iv) {
		setIv(iv);
		return this;
	}

	/**
	 * 获取敏感数据对应的云 ID，开通云开发的小程序才会返回
	 * 
	 * @return 敏感数据对应的云 ID，开通云开发的小程序才会返回
	 */
	public String getCloudID() {
		return cloudID;
	}

	/**
	 * 设置敏感数据对应的云 ID，开通云开发的小程序才会返回
	 * 
	 * @param cloudID 敏感数据对应的云 ID，开通云开发的小程序才会返回
	 */
	public void setCloudID(String cloudID) {
		this.cloudID = cloudID;
	}

	/**
	 * 链式添加敏感数据对应的云 ID，开通云开发的小程序才会返回
	 * 
	 * @param cloudID 敏感数据对应的云 ID，开通云开发的小程序才会返回
	 * @return 对象本身
	 */
	public UserProfile chainCloudID(String cloudID) {
		setCloudID(cloudID);
		return this;
	}

}
