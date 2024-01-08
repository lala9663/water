//package com.meta.metaway.user.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//import com.meta.metaway.jwt.JWTUtil;
//import com.meta.metaway.user.model.User;
//import com.meta.metaway.user.service.IUserService;
//
//@Controller
//public class UserController2 {
//
//	@Autowired
//	IUserService userService;
//    
//	@Autowired
//    private JWTUtil jwtUtil;
//	
//	@GetMapping("/profile")
//	public String getProfilePage(@RequestHeader("Authorization") String token, Model model) {
//	    System.out.println("컨트롤러: " + token);
//	    
//	    if (token != null && token.startsWith("Bearer ")) {
//	        String jwtToken = token.substring(7); // "Bearer " 부분을 제외한 토큰 문자열 추출
//
//	        if (jwtUtil.isExpired(jwtToken)) {
//	            String account = jwtUtil.getUsername(jwtToken); 
//	            User user = userService.getUserByUsername(account); 
//
//	            if (user != null) {
//	                model.addAttribute("user", user); // 모델에 사용자 정보를 추가
//	                return "profile"; // 프로필 페이지로 이동
//	            }
//	        }
//	    }
//
//	    return "redirect:/login"; // 로그인 페이지로 리다이렉트
//	}
//
//}
