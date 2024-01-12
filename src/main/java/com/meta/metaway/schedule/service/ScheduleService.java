package com.meta.metaway.schedule.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.schedule.dao.IScheduleRepository;
import com.meta.metaway.schedule.model.Schedule;

@Service
public class ScheduleService implements IScheduleService{

	@Autowired 
	IScheduleRepository scheduleRepository;
	
	@Autowired
	IAdminRepository adminRepository;

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
	        adminRepository.updateStaffStatus(staffId);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
}