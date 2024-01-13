package com.meta.metaway.admin.service;

import java.util.List;

import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.dto.SoldRankDTO;

public interface IAdminService {
	List<AdminOrderDTO> findAllOrderList(int page);
	int selectTotalOrdersCount();
	int selectWaitingOrdersCount();
	int selectCompleteOrdersCount();
	List<AdminOrderDTO> searchOrderListByKeyword(String keyword, Integer orderState, String orderDate, int page);
	void updateCancleOrder(long orderId);
	void updateCompleteOrder(long orderId);
	int getOrderId(long orderId);
	AdminOrderDetailDTO selectOneOrderList(long orderId);
	//여러개 주문 목록조회
	List<AdminOrderDetailDTO> selectAllOrderList(long orderId);
	List<AdminStaffDTO> selectAllCodiList();
	List<AdminStaffDTO> selectAllDriverList();
	//주문에 해당하는 일정조회
	List<AdminScheduleStaffDTO> selectListScheduleStaff(long orderId);
	void deleteSchedule(long orderId, long staffId);
	int getStaffId(long staffId);
	
    List<SoldRankDTO> getSoldRankProductWithImage(int orderState);
    List<SoldRankDTO> getSoldRankProductWithoutImage(int orderState);


}
