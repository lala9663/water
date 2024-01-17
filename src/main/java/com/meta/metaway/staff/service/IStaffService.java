package com.meta.metaway.staff.service;

import java.time.LocalDateTime;
import java.util.List;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.schedule.model.Schedule;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.dto.StaffScheduleDTO;
import com.meta.metaway.staff.model.Staff;

public interface IStaffService {
	
//	조건부 세팅하기
	void settingScheduleDate(StaffScheduleDTO staff);
	
    boolean isCodiOrDriver(String account);
    Long getUserIdByAccount(String account);
    
    //담당 회원 주문 목록 조회
	List<StaffListDTO> getOrderProductList();
	List<Product> getProductForStaff(String account);
	
    String getUserAuthority(int userId);

    void createWorkPlace(long userId, String workPlace);
    
    void updateWorkPlace(long userId, String workPlace);

    String getCurrentWorkPlace(long userId);
    
    //yoon---------------------------------
    
    //기사 할일 목록
    List<StaffScheduleDTO> getDriverTodoList(long userId);
    //기사 방문예정일 선택
    void driverDatePick(StaffScheduleDTO staffSchedule);
    //업무가 완료되었을때
    void completeSchedule(StaffScheduleDTO staff);
}
