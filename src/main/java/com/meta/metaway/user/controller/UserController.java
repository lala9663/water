package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	// 내 정보 조회
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

    // 정보 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @RequestBody User updatedUser) {

    	String username = jwtUtil.getUsername(token);
    	
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    	String newPassword = encoder.encode(updatedUser.getPassword());
        
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
        	existingUser.setPassword(newPassword);
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setAddress(updatedUser.getAddress());

            userService.updateUser(existingUser);
            
            return ResponseEntity.ok("변경 성공");
        } else {
            return ResponseEntity.badRequest().body("변경 실패");
        }
    }
    
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
//        String username = jwtUtil.getUsername(token);
//        
//        User existingUser = userService.getUserByUsername(username);
//        if (existingUser != null) {
//            userService.deleteUserByAccount(username);
//            return ResponseEntity.ok("사용자 삭제 성공");
//        } else {
//            return ResponseEntity.badRequest().body("삭제 실패");
//        }
//    }


}
