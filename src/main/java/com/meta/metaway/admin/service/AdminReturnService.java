package com.meta.metaway.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.admin.dao.IAdminReturnRepository;
import com.meta.metaway.admin.dto.AdminReturnDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;

@Service
public class AdminReturnService implements IAdminReturnService {
	
	@Autowired
	IAdminReturnRepository adminReturnRepository;
	
	@Override
	public int selectTotalReturnCount() {
		return adminReturnRepository.selectTotalReturnCount();
	}

	@Override
	public List<AdminReturnDTO> selectAllReturnList(int page) {
		int start =(page-1)*10 +1;
		return adminReturnRepository.selectAllReturnList(start, start+9);
	}

	@Override
	public int selectReturnCount() {
		return adminReturnRepository.selectReturnCount();
	}

	@Override
	public int selectTerminateCount() {
		return adminReturnRepository.selectTerminateCount();
	}

	@Override
	public int selectRefundCount() {
		return adminReturnRepository.selectRefundCount();
	}

	@Override
	public int getOrderDetailIdByReturn(long returnId) {
		return adminReturnRepository.getOrderDetailIdByReturn(returnId);
	}

	@Override
	public int getReturnId(long returnId) {
		return adminReturnRepository.getReturnId(returnId);
	}

	@Override
	public List<AdminReturnDTO> selectAllReturnDetailList(long orderDetailId) {
		return adminReturnRepository.selectAllReturnDetailList(orderDetailId);
	}

	@Override
	public List<AdminScheduleStaffDTO> selectListReturnScheduleStaff(long orderDetailId) {
		return adminReturnRepository.selectListReturnScheduleStaff(orderDetailId);
	}

	

}
