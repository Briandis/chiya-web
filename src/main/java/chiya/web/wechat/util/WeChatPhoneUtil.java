package chiya.web.wechat.util;

import com.alibaba.fastjson.JSON;

import chiya.core.base.exception.Assert;
import chiya.web.request.http.RequestUtil;
import chiya.web.wechat.entity.PhoneCodeEntity;
import chiya.web.wechat.entity.ResultPhone;
import chiya.web.wechat.entity.WechatAPI;

public class WeChatPhoneUtil {
	/**
	 * 通用小程序微信都录验证
	 * 
	 * @param code 手机号
	 * @return
	 */
	public static String getPhoneNumber(String code) {
		String bodyAsString = RequestUtil.post(WechatAPI.GET_PHONE + WeChatAccessTokenUtil.getToken(), new PhoneCodeEntity(code));
		ResultPhone resultPhone = JSON.parseObject(bodyAsString, ResultPhone.class);
		Assert.isNull(resultPhone.getPhone_info(), "手机号获取失败," + resultPhone.getErrmsg());
		return resultPhone.getPhone_info().getPurePhoneNumber();
	}
}
