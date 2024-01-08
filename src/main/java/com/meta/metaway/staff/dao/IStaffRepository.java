package com.meta.metaway.staff.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.staff.model.Staff;

@Repository
@Mapper
public interface IStaffRepository {
    Long getIdByAccount(String account);
    
    String getUserAuthority(Long userId);
    
//    void checkIfUserIsCodiOrDriver(Long id);

    Long getUserIdByAccount(String account);

    void createWorkPlace(Staff staff);

    void updateWorkPlace(Map<String, Object> updateParams);
    
	long selectStaffMaxNo();
}
