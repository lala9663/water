package com.meta.metaway.staff.service;

import java.util.List;

import com.meta.metaway.staff.dto.StaffDTO;
import com.meta.metaway.staff.model.staff;
import com.meta.metaway.user.model.User;

public interface IStaffService {
	User getUserDetails(Long Userid);
	
}
