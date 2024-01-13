package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.metaway.admin.service.IAdminService;
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
	
	@Autowired
	private IAdminService adminService;

    @GetMapping("/")
    public String index(Model model) {
        // 오늘의 방문자 수 조회
        long todayVisitorCount = adminService.getDailyVisitorCount();

        // Redis에서 일일 방문자 수를 증가시킴
        adminService.increaseDailyVisitorCount();

        model.addAttribute("todayVisitorCount", todayVisitorCount);

        return "index";
    }
    @GetMapping("/join")
    public String getJoinPage(Model model) {
        model.addAttribute("joinDTO", new JoinDTO());

        return "user/join"; 
    }
    
//    @PostMapping("/join")
//    @ResponseBody
//    public ResponseEntity<String> saveUser(@RequestBody JoinDTO joinDTO) {
//        // 주소 정보를 저장하는 로직
//        userService.joinProcess(joinDTO);
//
//        // 성공적으로 저장되었을 때
//        return new ResponseEntity<>("User saved successfully", HttpStatus.OK);
//
//        // 실패했을 때
//        // return new ResponseEntity<>("Failed to save user", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    
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