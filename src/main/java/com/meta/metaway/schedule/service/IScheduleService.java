package com.meta.metaway.schedule.service;

public interface IScheduleService {
	void assignStaff(long orderId, long staffId, long userId);
	
	void returnSchedule(long orderDetailId, long staffId, long userId);
	
	void deleteReturnSchedule(long orderDetailId, long staffId);
}