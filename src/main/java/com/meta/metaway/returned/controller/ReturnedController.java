package com.meta.metaway.returned.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.meta.metaway.returned.model.Returned;
import com.meta.metaway.returned.service.IReturnedService;

@Controller
public class ReturnedController {
	
	
	@Autowired
	IReturnedService returnedService;
	
	@PostMapping("user/return")
	void productReturn(Returned Returned) {
		returnedService.InsertReturnTable(Returned);
	}
	
	@PostMapping("user/returnDelete")
	void CancelproductReturn(Returned Returned) {
		
	}
}
