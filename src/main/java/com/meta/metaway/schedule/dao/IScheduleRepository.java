package com.meta.metaway.schedule.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.schedule.model.Schedule;

@Repository
@Mapper
public interface IScheduleRepository {
	void insertSchedule(Schedule schedule);
	int getNextMaxScheduleId();
	void returnSchedule(Schedule schedule);
	void deleteReturnSchedule(long orderDetailId, long staffId);
	int getOrderIdReturnSchedule(long orderDetailId);
	int getReturnIdReturnSchedule(long orderDetailId);
	void updateCodiTypeAndCycle(long scheduleId);
	int getScheduleIdFromStaff(long scheduleId);
}