package chiya.web.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import chiya.core.base.string.StringUtil;

/**
 * JwtToken生成的工具类<br>
 * JWT token的格式：header.payload.signature<br>
 * header的格式（算法、token的类型）：<br>
 * {"alg": "HS512","typ": "JWT"}<br>
 * payload的格式（用户名、创建时间、生成时间）：<br>
 * {"sub":"wang","created":1489079981393,"exp":1489684781}<br>
 * signature的生成算法：<br>
 * <p>
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * </p>
 */
public class TokenUtil {
	/**
	 * 加密的数据中内容
	 */
	private static final String CLAIM_KEY_USERNAME = "sub";
	/**
	 * 创建时间
	 */
	private static final String CLAIM_KEY_CREATED = "created";

	/** token过期时间，单位毫秒 */
	public static int timeOut = 1000 * 60 * 60 * 24 * 7;

	/** token加密所用的密钥 */
	public static String secret = "chiya-web-token";

	/**
	 * 根据负载生成JWT的token
	 */
	private static String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
			// 构造的对象
			.setClaims(claims).
			// 生成过期时间
			setExpiration(createTimeOutDate()).
			// 设置加密算法，以及密钥
			signWith(SignatureAlgorithm.HS512, secret).
			// 加密
			compact();
	}

	/**
	 * 从token中获取JWT中的负载
	 */
	private static Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
				// 设置加密所用的密钥
				.setSigningKey(secret)
				// 待解密的token
				.parseClaimsJws(token)
				// 获取解密后的对象
				.getBody();
		} catch (ExpiredJwtException e) {
			claims = e.getClaims();
		}
		return claims;
	}

	/**
	 * 生成token的过期时间
	 */
	private static Date createTimeOutDate() {
		return new Date(System.currentTimeMillis() + timeOut);
	}

	/**
	 * 从token中获取用户信息
	 * 
	 * @param token 加密token
	 * @return 解密后的数据
	 */
	public static String getData(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 判断token是否已经过期
	 * 
	 * @param token 待解密的token
	 * @return true:过期 / false:未过期
	 */
	private static boolean isTimeOut(String token) {
		// 获取token的过期时间
		Date expiredDate = getTokenTimeOutDate(token);
		// 如果token的过期时间小于当前时间，则过期
		return expiredDate.getTime() < System.currentTimeMillis();
	}

	/**
	 * 从token中获取过期时间
	 * 
	 * @param token 待解密的token
	 * @return Date 获取到的时间对象
	 */
	private static Date getTokenTimeOutDate(String token) {
		Claims claims = getClaimsFromToken(token);
		// 获取过期时间
		return claims.getExpiration();
	}

	/**
	 * 生成Token加密信息
	 * 
	 * @param data 用户标识信息
	 * @return 加密后的字符串
	 */
	public static String createToken(String data) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, data);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * 当原来的token没过期时是可以刷新的
	 *
	 * @param token 带tokenHead的token
	 */
	public static String refreshHeadToken(String token) {
		// 旧token不为空
		if (StringUtil.isNullOrZero(token)) { return null; }
		// 获取请求头后的数据
		// token校验不通过
		Claims claims = getClaimsFromToken(token);
		if (claims == null) { return null; }
		// 如果token已经过期，不支持刷新
		if (isTimeOut(token)) { return null; }
		// 如果token在30分钟之内刚刷新过，返回原token
		if (tokenRefreshJustBefore(token, 30 * 60)) {
			return token;
		} else {
			claims.put(CLAIM_KEY_CREATED, new Date());
			return generateToken(claims);
		}
	}

	/**
	 * 判断token在指定时间内是否刚刚刷新过
	 * 
	 * @param token 原token
	 * @param time  指定时间（秒）
	 * @return true:刷新过 / false:没刷新过
	 */
	private static boolean tokenRefreshJustBefore(String token, int time) {
		Claims claims = getClaimsFromToken(token);
		// 获取token中的创建时间
		Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
		return System.currentTimeMillis() - created.getTime() > time * 1000;

	}
}
