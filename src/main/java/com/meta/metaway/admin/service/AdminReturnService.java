package com.meta.metaway.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.admin.dao.IAdminReturnRepository;
import com.meta.metaway.admin.dto.AdminReturnDTO;

@Service
public class AdminReturnService implements IAdminReturnService {
	
	@Autowired
	IAdminReturnRepository adminReturnRepository;
	
	@Override
	public int selectTotalReturnCount() {
		return adminReturnRepository.selectTotalReturnCount();
	}

	@Override
	public List<AdminReturnDTO> selectAllReturnList() {
		return adminReturnRepository.selectAllReturnList();
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
	public int getOrderDetailId(long orderDetailId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getReturnId(long returnId) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
