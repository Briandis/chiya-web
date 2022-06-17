package chiya.web.wechat.util;

import com.alibaba.fastjson.JSON;

import chiya.web.request.http.RequestUtil;
import chiya.web.wechat.entity.WeChatLogin;
import chiya.web.wechat.entity.WeChatUser;
import chiya.web.wechat.entity.WechatAPI;

/**
 * 微信登录工具库
 * 
 * @author chiya
 */
public class WeChatLoginUtil {

	/**
	 * 通用小程序微信都录验证
	 * 
	 * @param weChatLogin 登录对象
	 * @return WechatUsert 微信登录对象
	 */
	public static WeChatUser jscode2session(WeChatLogin weChatLogin) {
		String bodyAsString = RequestUtil.get(WechatAPI.JS_CODE_2_SESSION, weChatLogin);
		return JSON.parseObject(bodyAsString, WeChatUser.class);
	}

}
