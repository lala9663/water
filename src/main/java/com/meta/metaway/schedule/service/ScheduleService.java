package com.meta.metaway.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dao.IAdminReturnRepository;
import com.meta.metaway.schedule.dao.IScheduleRepository;
import com.meta.metaway.schedule.model.Schedule;

@Service
public class ScheduleService implements IScheduleService{

	@Autowired 
	IScheduleRepository scheduleRepository;
	
	@Autowired
	IAdminRepository adminRepository;
	
	@Autowired
	IAdminReturnRepository adminReturnRepository;

	@Override
    @Transactional
	public void assignStaff(long orderId, long staffId ,long userId) {
		try {
			System.out.println("insert start");
			Schedule schedule = new Schedule();
			schedule.setScheduleId(scheduleRepository.getNextMaxScheduleId());
			schedule.setVisitCycle(0);
			schedule.setVisitType(0);
			schedule.setVisitState(0);
			schedule.setUserId(userId);
			schedule.setOrderId(orderId);
	        schedule.setStaffId(staffId);
	        System.out.println(schedule.toString());
	        scheduleRepository.insertSchedule(schedule);
//	        adminRepository.updateStaffStatus(staffId);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void deleteReturnSchedule(long orderDetailId, long staffId) {
		adminRepository.deleteSchedule(orderDetailId, staffId);
		adminRepository.updateStaffStatus(staffId);
	}

	@Override
	@Transactional
	public void returnSchedule(long orderDetailId, long staffId, long userId) {
		try {
			System.out.println("return start");
			System.out.println(scheduleRepository.getReturnIdReturnSchedule(orderDetailId));
			System.out.println(scheduleRepository.getOrderIdReturnSchedule(orderDetailId));
			Schedule schedule = new Schedule();
			schedule.setScheduleId(scheduleRepository.getNextMaxScheduleId());
			schedule.setVisitCycle(0);
			schedule.setVisitType(0);
			schedule.setVisitState(0);
			schedule.setUserId(userId);
			schedule.setOrderId(scheduleRepository.getOrderIdReturnSchedule(orderDetailId));
			schedule.setOrderDetailId(orderDetailId);
			schedule.setReturnId(scheduleRepository.getReturnIdReturnSchedule(orderDetailId));
	        schedule.setStaffId(staffId);
	        System.out.println(schedule.toString());
	        scheduleRepository.returnSchedule(schedule);
	        
//	        adminRepository.updateStaffStatus(staffId);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void updateCodiTypeAndCycle(long scheduleId ) {
		scheduleRepository.updateCodiTypeAndCycle(scheduleId);
	}

	@Override
	public int getScheduleIdFromStaff(long scheduleId) {
		return scheduleRepository.getScheduleIdFromStaff(scheduleId);
	}

	@Override
	public void assignCodiStaff(long orderId, long staffId, long userId) {
		try {
			System.out.println("cody insert start");
			Schedule schedule = new Schedule();
			schedule.setScheduleId(scheduleRepository.getNextMaxScheduleId());
			schedule.setVisitCycle(2);
			schedule.setVisitType(1);
			schedule.setVisitState(0);
			schedule.setUserId(userId);
			schedule.setOrderId(orderId);
	        schedule.setStaffId(staffId);
	        System.out.println(schedule.toString());
	        scheduleRepository.insertSchedule(schedule);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
}