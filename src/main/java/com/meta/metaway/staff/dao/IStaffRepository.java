package com.meta.metaway.staff.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.schedule.model.Schedule;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.dto.StaffScheduleDTO;
import com.meta.metaway.staff.model.Staff;

@Repository
@Mapper
public interface IStaffRepository {
    Long getIdByAccount(String account);
        
//    void checkIfUserIsCodiOrDriver(Long id);

    Long getUserIdByAccount(String account);
    
	long selectStaffMaxNo();
	

	//회워 주문 목록 리스트2
	List<StaffListDTO> selectOrderProductList();
	List<Product> getProductForStaff(Long userId);

    String getUserAuthority(long userId);

    void createWorkPlace(Staff staff);
    
    void updateWorkPlace(Staff staff);

    String getWorkPlaceByUserId(long userId);
    
    Staff getStaffByUserId(long userId);
    
    //yoon----------------------
    //기사 할일 목록
    List<StaffScheduleDTO> getDriverTodoList(long userId);
    //기사 방문예정일 선택 업데이트
    void driverDatePick(StaffScheduleDTO staffSchedule);
    //스케쥴 가져오기
    StaffScheduleDTO selectScheduleDriver (long scheduleId);
    
//    오더 디테일 아이디를 통해 스케쥴 변경하기
    void settingScheduleDateByorderDetailId(StaffScheduleDTO staff);
    
    void updateScheduleState(StaffScheduleDTO staff);
    
    void updateOrderState(StaffScheduleDTO staff);
    
    void updateOrderDetailState(StaffScheduleDTO staff);
    
    void updateOrderDetailDate(Contract contract);
    
    Contract getOrderDetailContractYear(StaffScheduleDTO staff);
    
}
