package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

//    // 정보 수정
//    @PutMapping("/update")
//    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @RequestBody User updatedUser) {
//
//    	String username = jwtUtil.getUsername(token);
//    	
//    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        
//        User existingUser = userService.getUserByUsername(username);
//        
//    	String newPassword = encoder.encode(updatedUser.getPassword());
//        if (existingUser != null) {
//        	existingUser.setPassword(newPassword);
//            existingUser.setName(updatedUser.getName());
//            existingUser.setEmail(updatedUser.getEmail());
//            existingUser.setPhone(updatedUser.getPhone());
//            existingUser.setAge(updatedUser.getAge());
//            existingUser.setAddress(updatedUser.getAddress());
//
//            userService.updateUser(existingUser);
//            
//            return ResponseEntity.ok("변경 성공");
//        } else {
//            return ResponseEntity.badRequest().body("변경 실패");
//        }
//    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        String username = jwtUtil.getUsername(token);

        if (!username.isEmpty()) {
            try {
                User updated = userService.updateUser(username, user);
                if (updated != null) {
                    return ResponseEntity.ok("사용자 정보가 업데이트되었습니다.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }
    }
  
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsername(token);
        
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            userService.deleteUserByAccount(username);
            return ResponseEntity.ok("사용자 삭제 성공");
        } else {
            return ResponseEntity.badRequest().body("삭제 실패");
        }
    }
    
    // 아직 미완성
    @PostMapping("/check-password")
    public ResponseEntity<String> checkPassword(@RequestHeader("Authorization") String token, @RequestBody String password) {
        String username = jwtUtil.getUsername(token);

        try {
            boolean passwordMatches = userService.checkPasswordByAccount(username, password);
            if (passwordMatches) {
                return ResponseEntity.ok("비밀번호 일치");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 불일치");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("오류: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {

    	
        return ResponseEntity.ok("로그아웃 성공");
    }


}
