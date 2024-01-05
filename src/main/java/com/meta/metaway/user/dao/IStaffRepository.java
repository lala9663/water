package com.meta.metaway.user.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.user.model.Staff;

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
