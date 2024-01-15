package com.meta.metaway.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.meta.metaway.admin.dto.AdminStaffDTO;

@Repository
@Mapper
public interface IAdminStaffRepository {
	//전체 직원리스트 조회
	List<AdminStaffDTO> selectAllStaff(@Param("start") Integer start, @Param("end") Integer end);
	
	List<AdminStaffDTO> searchAllStaff(@Param("keyword") String keyword, String authorityName, @Param("start") int start,
			@Param("end") int end);
	//전체 직원수 조회
	int selectTotalStaffCount();
}
