package com.meta.metaway.schedule.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.metaway.schedule.service.IScheduleService;
@RequestMapping("/schedule")
@Controller
public class ScheduleController {

	@Autowired
	IScheduleService scheduleService;

	@PostMapping("/driver/{orderId}/{staffId}")
	@ResponseBody
	public ResponseEntity<String> assignDriver(@PathVariable long orderId, @PathVariable long staffId, @RequestBody Map<String, Long> schedule) {
		System.out.println("기사배정 컨트롤러");
		System.out.println(schedule.get("userId"));
		try {
			long userId = schedule.get("userId");
			System.out.println(orderId+","+staffId+","+userId);
			scheduleService.assignStaff(orderId, staffId, userId);
			return new ResponseEntity<>("기사 배정 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/codi/{orderId}/{staffId}")
	@ResponseBody
	public ResponseEntity<String> assignCodi(@PathVariable long orderId, @PathVariable long staffId, @RequestBody Map<String, Long> schedule) {
		System.out.println("코디배정 컨트롤러");
		System.out.println(schedule.get("userId"));
		System.out.println(schedule.get("value"));
		try {
			long userId = schedule.get("userId");
			System.out.println(orderId+","+staffId+","+userId);
			scheduleService.assignCodiStaff(orderId, staffId, userId);
			return new ResponseEntity<>("코디 배정 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	


}