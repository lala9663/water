package com.meta.metaway.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dto.OrderDTO;
import com.meta.metaway.admin.dto.OrderDetailDTO;
import com.meta.metaway.admin.dto.StaffDTO;

@Service
public class AdminService implements IAdminService {
	
	@Autowired
	IAdminRepository adminRepository;
	
	@Override
	public List<OrderDTO> findAllOrderList(int page) {
		int start =(page-1)*10 +1;
		return adminRepository.findAllOrderList(start, start+9);
	}

	@Override
	public int selectTotalOrdersCount() {
		return adminRepository.selectTotalOrdersCount();
	}

	@Override
	public int selectWaitingOrdersCount() {
		return adminRepository.selectWaitingOrdersCount();
	}

	@Override
	public List<OrderDTO> searchOrderListByKeyword(String keyword, Integer orderState, String orderDate, int page) {
		int start = (page-1)*10 + 1;
		return adminRepository.searchOrderListByKeyword("%"+keyword+"%", orderState, orderDate, start, start+9);
	}

	@Override
	public void updateCancleOrder(long orderId) {
		adminRepository.updateCancleOrder(orderId);
	}

	@Override
	public int selectCompleteOrdersCount() {
		return adminRepository.selectCompleteOrdersCount();
	}

	@Override
	public int getOrderId(long orderId) {
		return adminRepository.getOrderId(orderId);
	}

	@Override
	public OrderDetailDTO selectOneOrderList(long orderId) {
		return adminRepository.selectOneOrderList(orderId);
	}

	@Override
	public void updateCompleteOrder(long orderId) {
		adminRepository.updateCompleteOrder(orderId);
	}

	@Override
	public List<StaffDTO> selectAllCodiList() {
		// TODO Auto-generated method stub
		return adminRepository.selectAllCodiList();
	}

	@Override
	public List<StaffDTO> selectAllDriverList() {
		// TODO Auto-generated method stub
		return adminRepository.selectAllDriverList();
	}
	
}
