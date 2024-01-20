package com.meta.metaway.schedule.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dao.IAdminReturnRepository;
import com.meta.metaway.schedule.dao.IScheduleRepository;
import com.meta.metaway.schedule.model.Schedule;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
	@Mock
	private IAdminRepository adminRepository;

	@Mock
	private IAdminReturnRepository adminReturnRepository;

	@Mock
	private IScheduleRepository scheduleRepository;
	@InjectMocks
	private ScheduleService scheduleService;


	
	@DisplayName("기사 배정")
	@Test
	public void testAssignStaff() {
		  long orderId = 1L, staffId = 1L, userId = 1L;

	        // 실행
	        scheduleService.assignStaff(orderId, staffId, userId);

	        // 검증
	        verify(scheduleRepository).getNextMaxScheduleId();
	        verify(scheduleRepository).insertSchedule(any(Schedule.class));
	}
	@DisplayName("반납 스케쥴 삭제")
	@Test
	public void testDeleteReturnSchedule() {
	       long orderDetailId = 1L, staffId = 1L;

	        // 실행
	        scheduleService.deleteReturnSchedule(orderDetailId, staffId);

	        // 검증
	        verify(adminRepository).deleteSchedule(orderDetailId, staffId);
	        verify(adminRepository).updateStaffStatus(staffId);
	}
	@DisplayName("반납 스케쥴 생성")
	@Test
	public void testReturnSchedule() {
		long orderDetailId = 1L, staffId = 1L, userId = 1L;
        long returnId = 2L, orderId = 3L, nextScheduleId = 4L;

        when(scheduleRepository.getReturnIdReturnSchedule(orderDetailId)).thenReturn((int)returnId);
        when(scheduleRepository.getOrderIdReturnSchedule(orderDetailId)).thenReturn((int)orderId);
        when(scheduleRepository.getNextMaxScheduleId()).thenReturn((int)nextScheduleId);

        scheduleService.returnSchedule(orderDetailId, staffId, userId);

        verify(scheduleRepository).getReturnIdReturnSchedule(orderDetailId);
        verify(scheduleRepository).getOrderIdReturnSchedule(orderDetailId);
        verify(scheduleRepository).getNextMaxScheduleId();
        verify(scheduleRepository).returnSchedule(any(Schedule.class));
	}
	  @DisplayName("코디 스케쥴 타입 변경, 주기 변경")	
	  @Test
	    void testUpdateCodiTypeAndCycle() {
	        long scheduleId = 1L;

	        scheduleService.updateCodiTypeAndCycle(scheduleId);

	        verify(scheduleRepository).updateCodiTypeAndCycle(scheduleId);
	    }
	    @DisplayName("스케쥴 담당 직원 조회")
	    @Test
	    void testGetScheduleIdFromStaff() {
	        long scheduleId = 1L;

	        scheduleService.getScheduleIdFromStaff(scheduleId);

	        verify(scheduleRepository).getScheduleIdFromStaff(scheduleId);
	    }
	    @DisplayName("코디 배정")
	    @Test
	    void testAssignCodiStaff() {
	        long orderId = 1L, staffId = 1L, userId = 1L;

	        scheduleService.assignCodiStaff(orderId, staffId, userId);

	        verify(scheduleRepository).getNextMaxScheduleId();
	        verify(scheduleRepository).insertCodiSchedule(any(Schedule.class));
	    }

}
