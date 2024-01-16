package com.meta.metaway.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meta.metaway.admin.dto.AdminReturnDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.service.IAdminReturnService;
import com.meta.metaway.admin.service.IAdminService;
import com.meta.metaway.schedule.service.IScheduleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminReturnController {
	
	@Autowired
	IAdminReturnService adminReturnService;
	
	@Autowired
	IScheduleService scheduleService;
	
	@Autowired
	IAdminService adminService;
	
	@GetMapping("/returnlist/{page}")
	public String returnList(@PathVariable int page, HttpSession session, Model model) {
		System.out.println("반납관리");
		session.setAttribute("page", page);
		try {
			int totReturn = adminReturnService.selectTotalReturnCount();
			model.addAttribute("totReturn", totReturn);
			
			List<AdminReturnDTO> returnList= adminReturnService.selectAllReturnList(page);
			model.addAttribute("returnList", returnList);
			
			int totReturnOffer = adminReturnService.selectReturnCount();
			model.addAttribute("totReturnOffer", totReturnOffer);
			
			int totTerminateOffer = adminReturnService.selectTerminateCount();
			model.addAttribute("totTerminateOffer", totTerminateOffer);
			
			int totRefundOffer = adminReturnService.selectRefundCount();
			model.addAttribute("totRefundOffer", totRefundOffer);
			
			int totalPage = 0;
			if (totReturn > 0) {
				totalPage = (int) Math.ceil(totReturn / 10.0);
			}
			int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
			int nowPageBlock = (int) Math.ceil(page / 10.0);
			int startPage = (nowPageBlock - 1) * 10 + 1;
			int endPage = 0;
			if (totalPage > nowPageBlock * 10) {
				endPage = nowPageBlock * 10;
			} else {
				endPage = totalPage;
			}
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("nowPage", page);
			model.addAttribute("totalPageBlock", totalPageBlock);
			model.addAttribute("nowPageBlock", nowPageBlock);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "admin/rentalmanage";
	}
	
	
	
	@GetMapping("/return/{returnId}")
	public String returnDetail(@PathVariable long returnId,  Model model) {
		System.out.println("반납상세페이지");
		System.out.println(returnId + ": retrunId");
		try {
			List<AdminReturnDTO> returnDetailList = adminReturnService.selectAllReturnDetailList(returnId);
			System.out.println(returnDetailList.toString());
			model.addAttribute("returns",returnDetailList);
			
			List<AdminStaffDTO> driverList = adminService.selectAllDriverList();
			model.addAttribute("driverList", driverList);
			
			int orderDetailId = adminReturnService.getOrderDetailIdByReturn(returnId);
			List<AdminScheduleStaffDTO> staffList = adminReturnService.selectListReturnScheduleStaff(orderDetailId);
			System.out.println(staffList.toString());
			model.addAttribute("staff",staffList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/returnDetail";
	}
	
	@PostMapping("/return/driver/{orderDetailId}/{staffId}")
	@ResponseBody
	public ResponseEntity<String> assignDriver(@PathVariable long orderDetailId, @PathVariable long staffId, @RequestBody Map<String, Long> schedule) {
		System.out.println("반납기사 배정 컨트롤러");
		System.out.println(schedule.get("userId"));
		try {
			long userId = schedule.get("userId");
			System.out.println(orderDetailId+","+staffId+","+userId);
			scheduleService.returnSchedule(orderDetailId, staffId, userId);
			return new ResponseEntity<>("반납기사 배정 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@PostMapping("/cancelReturn/{orderDetailId}/{staffId}")
//	public String cancleScheduleDriver(@PathVariable long orderDetailId ,@PathVariable long staffId, RedirectAttributes redirectattr) {
//		System.out.println("배정 삭제 컨트롤러");
//		adminService.deleteSchedule(orderDetailId, staffId);
//		redirectattr.addFlashAttribute("message", orderDetailId+" 번 배정이 취소되었습니다.");
//		return "redirect:/admin/assign/{orderId}";
//	}
	
}
