package com.meta.metaway.admin.service;

import java.util.List;

import com.meta.metaway.admin.dto.OrderDTO;
import com.meta.metaway.admin.dto.OrderDetailDTO;
import com.meta.metaway.admin.dto.StaffDTO;

public interface IAdminService {
	List<OrderDTO> findAllOrderList(int page);
	int selectTotalOrdersCount();
	int selectWaitingOrdersCount();
	int selectCompleteOrdersCount();
	List<OrderDTO> searchOrderListByKeyword(String keyword, Integer orderState, String orderDate, int page);
	void updateCancleOrder(long orderId);
	void updateCompleteOrder(long orderId);
	int getOrderId(long orderId);
	OrderDetailDTO selectOneOrderList(long orderId);
	List<StaffDTO> selectAllCodiList();
	List<StaffDTO> selectAllDriverList();
	
}
