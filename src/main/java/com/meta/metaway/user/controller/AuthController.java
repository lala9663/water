package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.service.UserService;

@RestController
public class AuthController {

	@Autowired
	UserService userService;


    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody JoinDTO joinDTO) {
        boolean isExist = userService.checkIfUserExists(joinDTO.getAccount());

        if (isExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재합니다");
        } else {
            userService.joinProcess(joinDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입되었습니다");
        }
    }

}