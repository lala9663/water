package com.meta.metaway.jwt;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter   {


	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "account";

	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private boolean postOnly = true;


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		String account = obtainUsername(request);
		account = (account != null) ? account.trim() : "";
		System.out.println("커스텀 유저네임 필터: " + account);
		
		String password = obtainPassword(request);
		password = (password != null) ? password : "";
		
		UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(account,
				password);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

    @Override
    protected String obtainUsername(HttpServletRequest request) {

        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {

        return request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
    }

	
}
