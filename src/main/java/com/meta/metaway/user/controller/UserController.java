package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.user.model.User;
import com.meta.metaway.user.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService userService;
    
	@Autowired
    private JWTUtil jwtUtil;
	
    @GetMapping("/profile")
    public ResponseEntity<User> getUserByToken(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsername(token);
        System.out.println(username);
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/update")
//    public ResponseEntity<String> updateUser(@RequestBody User updatedUser) {
//        userService.updateUser(updatedUser);
//        return ResponseEntity.ok("수정되었습니다");
//    }
}
