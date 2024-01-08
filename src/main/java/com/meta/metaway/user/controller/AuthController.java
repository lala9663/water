package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.User;
import com.meta.metaway.user.service.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

	@Autowired
    private IUserService userService;
	
	@Autowired
	private JWTUtil jwtUtil;

    @GetMapping("/")
    public String index() {
    
    	return "index"; 
    }

    @GetMapping("/join")
    public String getJoinPage(Model model) {
        model.addAttribute("joinDTO", new JoinDTO());

        return "join"; 
    }

    @PostMapping("/join")
    public String joinUser(JoinDTO joinDTO) {
        userService.joinProcess(joinDTO);
        
        return "redirect:/login"; 
    }
    
	@GetMapping("/login")
	public String login() {
		  
	 return "/member/login";
	}
	
//	@GetMapping("/user/profile")
//	public String getProfilePage(HttpServletRequest request, Model model) {
//	    String tokenHeader = request.getHeader("Authorization");
//	    String token = null;
//
//	    if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
//	        token = tokenHeader.substring(7); 
//	    }
//	    System.out.println(token);
//	    if (token != null) {
//	        String username = jwtUtil.getUsername(token);
//	        User user = userService.getUserByUsername(username);
//
//	        if (user != null) {
//	            model.addAttribute("userProfile", user);
//	            return "member/profile";
//	        }
//	    }
//
//	    return "redirect:/login";
//	}

	@GetMapping("/user/profile")
	public String getProfilePage(HttpServletRequest request, Model model) {
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

	    if (token != null) {
	        String username = jwtUtil.getUsername(token);
	        User user = userService.getUserByUsername(username);

	        if (user != null) {
	        	System.out.println("투스트링: " + user.toString());
	        	
	            model.addAttribute("userProfile", user);
	            return "member/profile";
	        }
	    }

	    return "redirect:/login";
	}

}
