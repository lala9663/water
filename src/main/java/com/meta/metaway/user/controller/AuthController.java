package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.service.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
    private IUserService userService;

    @GetMapping("/")
    public String index() {
    
    	return "index"; 
    }

    @GetMapping("/join")
    public String getJoinPage(Model model) {
        model.addAttribute("joinDTO", new JoinDTO());

        return "user/join"; 
    }

    @PostMapping("/join")
    public String joinUser(JoinDTO joinDTO) {
        userService.joinProcess(joinDTO);
        
        return "redirect:/login"; 
    }
    
	@GetMapping("/login")
	public String login() {
		  
	 return "/user/login";
	}

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token") || cookie.getName().equals("userNumber")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

   
}