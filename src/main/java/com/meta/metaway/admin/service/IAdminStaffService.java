package com.meta.metaway.admin.service;

import java.util.List;

import com.meta.metaway.admin.dto.AdminStaffDTO;

public interface IAdminStaffService {
	List<AdminStaffDTO> findAllStaffList(int page);
	List<AdminStaffDTO> searchAllStaff(String keyword, String authorityName, int page);
	int selectTotalStaffCount();
}
