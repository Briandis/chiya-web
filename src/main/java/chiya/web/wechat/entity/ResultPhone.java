package chiya.web.wechat.entity;

public class ResultPhone {
	/** 错误码 */
	private Integer errcode;
	/** 错误提示信息 */
	private String errmsg;
	/** 用户手机号信息 */
	private PhoneInfo phone_info;

	public class PhoneInfo {
		/** 用户绑定的手机号（国外手机号会有区号） */
		private String phoneNumber;
		/** 没有区号的手机号 */
		private String purePhoneNumber;
		/** 区号 */
		private String countryCode;
		/** 数据水印 */
		private Watermark watermark;

		/**
		 * 获取用户绑定的手机号（国外手机号会有区号）
		 * 
		 * @return 用户绑定的手机号（国外手机号会有区号）
		 */
		public String getPhoneNumber() {
			return phoneNumber;
		}

		/**
		 * 设置用户绑定的手机号（国外手机号会有区号）
		 * 
		 * @param phoneNumber 用户绑定的手机号（国外手机号会有区号）
		 */
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		/**
		 * 链式添加用户绑定的手机号（国外手机号会有区号）
		 * 
		 * @param phoneNumber 用户绑定的手机号（国外手机号会有区号）
		 * @return 对象本身
		 */
		public PhoneInfo chainPhoneNumber(String phoneNumber) {
			setPhoneNumber(phoneNumber);
			return this;
		}

		/**
		 * 获取没有区号的手机号
		 * 
		 * @return 没有区号的手机号
		 */
		public String getPurePhoneNumber() {
			return purePhoneNumber;
		}

		/**
		 * 设置没有区号的手机号
		 * 
		 * @param purePhoneNumber 没有区号的手机号
		 */
		public void setPurePhoneNumber(String purePhoneNumber) {
			this.purePhoneNumber = purePhoneNumber;
		}

		/**
		 * 链式添加没有区号的手机号
		 * 
		 * @param purePhoneNumber 没有区号的手机号
		 * @return 对象本身
		 */
		public PhoneInfo chainPurePhoneNumber(String purePhoneNumber) {
			setPurePhoneNumber(purePhoneNumber);
			return this;
		}

		/**
		 * 获取区号
		 * 
		 * @return 区号
		 */
		public String getCountryCode() {
			return countryCode;
		}

		/**
		 * 设置区号
		 * 
		 * @param countryCode 区号
		 */
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}

		/**
		 * 链式添加区号
		 * 
		 * @param countryCode 区号
		 * @return 对象本身
		 */
		public PhoneInfo chainCountryCode(String countryCode) {
			setCountryCode(countryCode);
			return this;
		}

		/**
		 * 获取数据水印
		 * 
		 * @return 数据水印
		 */
		public Watermark getWatermark() {
			return watermark;
		}

		/**
		 * 设置数据水印
		 * 
		 * @param watermark 数据水印
		 */
		public void setWatermark(Watermark watermark) {
			this.watermark = watermark;
		}

		/**
		 * 链式添加数据水印
		 * 
		 * @param watermark 数据水印
		 * @return 对象本身
		 */
		public PhoneInfo chainWatermark(Watermark watermark) {
			setWatermark(watermark);
			return this;
		}

	}

	/**
	 * 获取错误码
	 * 
	 * @return 错误码
	 */
	public Integer getErrcode() {
		return errcode;
	}

	/**
	 * 设置错误码
	 * 
	 * @param errcode 错误码
	 */
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	/**
	 * 链式添加错误码
	 * 
	 * @param errcode 错误码
	 * @return 对象本身
	 */
	public ResultPhone chainErrcode(Integer errcode) {
		setErrcode(errcode);
		return this;
	}

	/**
	 * 获取错误提示信息
	 * 
	 * @return 错误提示信息
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * 设置错误提示信息
	 * 
	 * @param errmsg 错误提示信息
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * 链式添加错误提示信息
	 * 
	 * @param errmsg 错误提示信息
	 * @return 对象本身
	 */
	public ResultPhone chainErrmsg(String errmsg) {
		setErrmsg(errmsg);
		return this;
	}

	/**
	 * 获取用户手机号信息
	 * 
	 * @return 用户手机号信息
	 */
	public PhoneInfo getPhone_info() {
		return phone_info;
	}

	/**
	 * 设置用户手机号信息
	 * 
	 * @param phone_info 用户手机号信息
	 */
	public void setPhone_info(PhoneInfo phone_info) {
		this.phone_info = phone_info;
	}

	/**
	 * 链式添加用户手机号信息
	 * 
	 * @param phone_info 用户手机号信息
	 * @return 对象本身
	 */
	public ResultPhone chainPhone_info(PhoneInfo phone_info) {
		setPhone_info(phone_info);
		return this;
	}

}
