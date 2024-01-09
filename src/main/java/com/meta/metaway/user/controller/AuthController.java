package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.multiClass.MultiClass;
import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.User;
import com.meta.metaway.user.service.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

	@Autowired
    private IUserService userService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private MultiClass multiClass;

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


    @GetMapping("/user/profile")
    public String getProfilePage(HttpServletRequest request, Model model) {
        String token = multiClass.getToken(request);

        if (token != null) {
            String username = jwtUtil.getUsername(token);
            User user = userService.getUserByUsername(username);

            if (user != null) {
                model.addAttribute("userProfile", user);
                return "user/profile";
            }
        }

        return "redirect:/login";
    }

	    @GetMapping("/user/update")
	    public String getUpdateProfilePage(HttpServletRequest request, Model model) {
	        String token = multiClass.getToken(request);
	
	        if (token != null) {
	            String username = jwtUtil.getUsername(token);
	            User userProfile = userService.getUserByUsername(username);
	
	            if (userProfile != null) {
	                model.addAttribute("userProfile", userProfile);
	                return "user/updateProfile";
	            }
	        }
	
	        return "redirect:/login"; 
	    }
	
	    @PostMapping("/user/update")
	    public String updateUser(HttpServletRequest request, @ModelAttribute("userProfile") User updatedUser) {
	        String token = multiClass.getToken(request);
	
	        if (token != null) {
	            String username = jwtUtil.getUsername(token);
	            System.out.println("회원 수정에서 유저네임: " + username);
	            userService.updateUser(username, updatedUser);
	        }
	
	        return "redirect:/user/profile"; 
	    }
	    
	    @GetMapping("/user/delete")
	    public String getdeleteUser(HttpServletRequest request) {
	        String token = multiClass.getToken(request);
	        
	        if (token != null) {
		    	return "user/delete";

	        }
	    	return "user/delete";
	    }
	    
	    @PostMapping("/user/delete")
	    public String deleteUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("deleteUser") User deleteUser) {
	        String token = multiClass.getToken(request);
	        
	        if (token != null) {
	            Long id = jwtUtil.getId(token);
	            userService.deleteUserById(id);
	            
	            Cookie deleteCookie = new Cookie("token", null);
	            deleteCookie.setMaxAge(0); 
	            deleteCookie.setHttpOnly(true); 
	            deleteCookie.setPath("/"); 
	            response.addCookie(deleteCookie);
	        }
	    	
	    	return "redirect:/";
	    }
   
}