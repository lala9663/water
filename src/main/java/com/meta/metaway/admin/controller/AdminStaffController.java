package com.meta.metaway.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.service.IAdminService;
import com.meta.metaway.admin.service.IAdminStaffService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminStaffController {
	
	@Autowired
	IAdminStaffService adminStaffService;

	@Autowired
	IAdminService adminService;

	@GetMapping("/stafflist/{page}")
	public String adminStaffList(@PathVariable int page, HttpSession session, Model model) {
		System.out.println("직원관리페이지");
		session.setAttribute("page", page);
		try {
			List<AdminStaffDTO> staffList = adminStaffService.findAllStaffList(page);
			model.addAttribute("staffList", staffList);
			int totStaff = adminStaffService.selectTotalStaffCount();
			model.addAttribute("totStaff", totStaff);

			List<AdminStaffDTO> driverList = adminService.selectAllDriverList();
			model.addAttribute("driverList", driverList);

			List<AdminStaffDTO> codiList = adminService.selectAllCodiList();
			model.addAttribute("codiList", codiList);

			int totalPage = 0;
			if (totStaff > 0) {
				totalPage = (int) Math.ceil(totStaff / 10.0);
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
		} catch (Exception e) {
			e.printStackTrace();
//			model.addAttribute("error", "An error occurred.");
//            return "error";
		}
		return "admin/staffmanage";
	}

	@GetMapping("/searchstaff/{page}")
	public String searchStaffList(@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false) String authorityName, @PathVariable int page, HttpSession session,
			Model model) {
		System.out.println("직원검색페이지");
		session.setAttribute("page", page);
		try {
			List<AdminStaffDTO> staffList = adminStaffService.searchAllStaff(keyword, authorityName, page);
			model.addAttribute("staffList", staffList);
			int totStaff = adminStaffService.selectTotalStaffCount();
			model.addAttribute("totStaff", totStaff);

			List<AdminStaffDTO> driverList = adminService.selectAllDriverList();
			model.addAttribute("driverList", driverList);

			List<AdminStaffDTO> codiList = adminService.selectAllCodiList();
			model.addAttribute("codiList", codiList);

			int totalPage = 0;
			if (totStaff > 0) {
				totalPage = (int) Math.ceil(totStaff / 10.0);
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
			model.addAttribute("authorityName", authorityName);
			model.addAttribute("keyword", keyword);
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("nowPage", page);
			model.addAttribute("totalPageBlock", totalPageBlock);
			model.addAttribute("nowPageBlock", nowPageBlock);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
		} catch (Exception e) {
			e.printStackTrace();
//			model.addAttribute("error", "An error occurred.");
//            return "error";
		}
		return "admin/searchstaff";
	}

	
}
