package com.meta.metaway.staff.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meta.metaway.global.MultiClass;
import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.staff.dto.StaffScheduleDTO;
import com.meta.metaway.staff.service.IStaffService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private IStaffService staffService;
	@Autowired
	private MultiClass multiClass;
	
	@PostMapping("/settingDate")
	public String settingScheduleDate(HttpServletRequest request, Model model, @ModelAttribute StaffScheduleDTO staff) {
		System.out.println(staff.getVisitDate());
		staffService.settingScheduleDate(staff);
		return "redirect:/staff/driver/todo";
	}
	
	@PostMapping("/completeOrder")
	public String completeSchedule(HttpServletRequest request, Model model, @ModelAttribute StaffScheduleDTO staff) {
		staffService.completeSchedule(staff);
		return "redirect:/staff/driver/todo";
	}
	
	@GetMapping("/createWorkPlace")
	public String showCreateWorkPlaceForm(HttpServletRequest request, Model model) {
		String token = multiClass.getToken(request);
		if (token != null) {
			long userId = jwtUtil.getId(token);
			String account = jwtUtil.getUsername(token);
			model.addAttribute("account", account);

		}
		return "staff/createWorkPlace";
	}

	@PostMapping("/createWorkPlace")
	public String createWorkPlace(HttpServletRequest request, @RequestParam String workPlace) {
		String token = multiClass.getToken(request);
		long userId = jwtUtil.getId(token);

		staffService.createWorkPlace(userId, workPlace);

		return "redirect:/user/profile";
	}

	@GetMapping("/updateWorkPlace")
	public String showUpdateWorkPlaceForm(HttpServletRequest request, Model model) {
		String token = multiClass.getToken(request);
		if (token != null) {
			long userId = jwtUtil.getId(token);
			String account = jwtUtil.getUsername(token);

			String currentWorkPlace = staffService.getCurrentWorkPlace(userId);

			model.addAttribute("account", account);
			model.addAttribute("currentWorkPlace", currentWorkPlace);
		}

		return "staff/updateWorkPlace";
	}

	@PostMapping("/updateWorkPlace")
	public String updateWorkPlace(HttpServletRequest request, @RequestParam String workPlace) {
		String token = multiClass.getToken(request);
		long userId = jwtUtil.getId(token);

		staffService.updateWorkPlace(userId, workPlace);

		return "redirect:/user/profile";
	}

	@GetMapping("/driver/todo")
	public String getTodoList(HttpServletRequest request, Model model) {
		String token = multiClass.getToken(request);
		long userId = jwtUtil.getId(token);
		System.out.println(userId + ": 유저아이디");

		List<StaffScheduleDTO> dirverTodo = staffService.getDriverTodoList(userId);
		model.addAttribute("driver", dirverTodo);

		return "staff/drive-todo";
	}

	@PostMapping("/driver/delivery/{scheduleId}")
	public String delivery(HttpServletRequest request, @PathVariable long scheduleId,
			@RequestParam LocalDateTime visitDate,
			RedirectAttributes redirectAttr, Model model) {
		System.out.println("기사 날짜 지정 컨트롤러");
		String token = multiClass.getToken(request);
		long userId = jwtUtil.getId(token);
		System.out.println(userId + ": 유저아이디");
		
		StaffScheduleDTO staffSchedule = new StaffScheduleDTO();
        staffSchedule.setUserId(userId);
        staffSchedule.setScheduleId(scheduleId);
        staffSchedule.setVisitDate(visitDate);

        staffService.driverDatePick(staffSchedule);
		System.out.println(scheduleId + "," + visitDate + "scheduleId, visitDate");
		redirectAttr.addFlashAttribute("message", "배송 예정일이 지정되었습니다. " + visitDate);
		return "redirect:/driver/todo";
	}
	
	@GetMapping("/cody/list")
	public String getCodiTodoList(HttpServletRequest request, Model model) {
		String token = multiClass.getToken(request);
		long userId = jwtUtil.getId(token);
		System.out.println(userId + ": 유저아이디");

		List<StaffScheduleDTO> codyTodo = staffService.getCodyTodoList(userId);
		model.addAttribute("codi", codyTodo );

		return "staff/cody-todo";
	}
}
