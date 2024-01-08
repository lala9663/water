package com.meta.metaway.staff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.staff.dao.IStaffRepository;
import com.meta.metaway.staff.dto.StaffDTO;
import com.meta.metaway.staff.model.staff;
import com.meta.metaway.user.model.User;

@Service
public class StaffService implements IStaffService{
	
	private final IStaffRepository iStaffRepository;
	
	@Autowired
	public StaffService(IStaffRepository iStaffRepository) {
		this.iStaffRepository = iStaffRepository;
	}

	@Override
	public User getUserDetails(Long Userid) {
		// TODO Auto-generated method stub
		return null;
	}



	
	

}
