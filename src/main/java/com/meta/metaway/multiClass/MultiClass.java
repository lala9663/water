package com.meta.metaway.multiClass;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meta.metaway.jwt.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	
    public String getToken(HttpServletRequest request) {
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

        return token;
    }
    
    
    public void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie deleteCookie = new Cookie(cookieName, null);
        deleteCookie.setMaxAge(0); 
        deleteCookie.setHttpOnly(true); 
        deleteCookie.setPath("/");
        response.addCookie(deleteCookie);
    }


}
