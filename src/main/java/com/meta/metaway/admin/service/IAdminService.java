package com.meta.metaway.admin.service;

import java.time.LocalDate;
import java.util.List;

import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.dto.SoldRankDTO;
import com.meta.metaway.admin.dto.UserCountDTO;
import com.meta.metaway.admin.model.Visitor;
import com.meta.metaway.order.dto.OrderDTO;
import com.meta.metaway.staff.dto.StaffDTO;

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
	
	void deleteReturnSchedule(long orderDetailId, long staffId);
	int getOrderIdByStaffId(long staffId);
	
    List<SoldRankDTO> getSoldRankProductWithImage(int orderState);
    List<SoldRankDTO> getSoldRankProductWithoutImage(int orderState);
    UserCountDTO getTotalUser();
    int getTotalSalesCount(int orderState);

	void increaseDailyVisitorCount();
    long getDailyVisitorCount();
    void resetDailyVisitorCount(String key);
    
    Long getVisitorCountByDate(LocalDate visitDate);
    Double getOverallAverageVisitorCount();

    List<AdminOrderDTO> getDashboardOrderList();
    
//    List<StaffDTO> getUsersWithCodi();
    
    List<StaffDTO> getUsersWithCodi();
    
    void insertViewCount(Visitor data);
    
    long getTotalOrderPriceMonth();
    
    long getTotalRentalPriceMonth();
    
    void updateCompleteOrderDetail(long orderDetailId);

}
