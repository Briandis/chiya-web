package chiya.web.wechat.util;

import com.alibaba.fastjson.JSON;

import chiya.core.base.exception.Assert;
import chiya.core.base.thread.ThreadUtil;
import chiya.web.request.http.RequestUtil;
import chiya.web.wechat.entity.ResultAccessToken;
import chiya.web.wechat.entity.WeChatAccessToken;
import chiya.web.wechat.entity.WechatAPI;

/**
 * 微信接口授权
 * 
 * @author chiya
 */
public class WeChatAccessTokenUtil {
	/** 接口授权 */
	private static String access_token = null;

	/** token在这个时间戳后过期 */
	private static long effectiveTime = 0;

	/**
	 * 刷新Token
	 */
	private static void refreshToken() {
		String bodyAsString = RequestUtil.get(WechatAPI.ACCESS_TOKEN, new WeChatAccessToken());
		ResultAccessToken resultAccessToken = JSON.parseObject(bodyAsString, ResultAccessToken.class);
		Assert.isNull(resultAccessToken.getAccess_token(), resultAccessToken.getErrmsg());
		access_token = resultAccessToken.getAccess_token();
	}

	/**
	 * 获取token
	 * 
	 * @return token
	 */
	public static String getToken() {
		// 多1分支，这样可以避免
		long now = System.currentTimeMillis() + 60 * 1000;
		ThreadUtil.doubleCheckLock(() -> effectiveTime - now < 0, WeChatAccessTokenUtil.class, () -> refreshToken());
		return access_token;
	}
}
