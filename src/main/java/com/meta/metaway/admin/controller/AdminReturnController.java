package com.meta.metaway.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meta.metaway.admin.dto.AdminReturnDTO;
import com.meta.metaway.admin.service.IAdminReturnService;

@Controller
@RequestMapping("/admin")
public class AdminReturnController {
	
	@Autowired
	IAdminReturnService adminReturnService;
	
	@GetMapping("/returnlist")
	public String returnList(Model model) {
		System.out.println("반납관리");
		int totReturn = adminReturnService.selectTotalReturnCount();
		model.addAttribute("totReturn", totReturn);
		
		List<AdminReturnDTO> returnList= adminReturnService.selectAllReturnList();
		model.addAttribute("returnList", returnList);
		
		int totReturnOffer = adminReturnService.selectReturnCount();
		model.addAttribute("totReturnOffer", totReturnOffer);
		
		int totTerminateOffer = adminReturnService.selectTerminateCount();
		model.addAttribute("totTerminateOffer", totTerminateOffer);
		
		int totRefundOffer = adminReturnService.selectRefundCount();
		model.addAttribute("totRefundOffer", totRefundOffer);
		
		return "admin/rentalmanage";
	}
	
	@GetMapping("/return/{returnId}")
	public String returnDetail(@PathVariable long returnId, RedirectAttributes redirectAttr, Model model) {
		System.out.println("반납상세페이지");
		
			
		return "admin/returnDetail";
	}
}
