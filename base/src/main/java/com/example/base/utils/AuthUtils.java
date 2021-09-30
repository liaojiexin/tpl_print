package com.example.base.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;

//拿到请求头的token来获取用户信息
public class AuthUtils {
	public static String getReqUser(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		String token = StringUtils.substringAfter(header, "bearer");
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey("SigningKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
		String localUser = (String) claims.get("userinfo");
		// 拿到当前用户
		return localUser;
	}
}