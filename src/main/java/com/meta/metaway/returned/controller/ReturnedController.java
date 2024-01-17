package com.meta.metaway.returned.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.meta.metaway.returned.model.Returned;
import com.meta.metaway.returned.service.IReturnedService;

@Controller
public class ReturnedController {
	
	
	@Autowired
	IReturnedService returnedService;
	
	@PostMapping("user/return")
	ResponseEntity<?> productReturn(@ModelAttribute Returned returned) {
		try {
			returnedService.InsertReturnTable(returned);
			return ResponseEntity.ok().body("신청 완료");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("신청 실패");
		}
	}
	
	@PostMapping("user/returnDelete")
	ResponseEntity<?> CancelproductReturn(@ModelAttribute Returned returned) {
		try {
			System.out.println(returned.getStateType());
			returnedService.CancelproductReturn(returned);
			return ResponseEntity.ok().body("취소 완료");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("취소 실패");
		} 
		
	}
}
