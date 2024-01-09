package com.meta.metaway.multiClass;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meta.metaway.jwt.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class MultiClass {

	@Autowired
	private JWTUtil jwtUtil;

	
	public long getTokenUserId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					token = cookie.getValue();
					break;
				}
			}
		}

		return jwtUtil.getId(token);
	}

}
