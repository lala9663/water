package com.meta.metaway.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.dto.SoldRankDTO;

@Repository
@Mapper
public interface IAdminRepository {
	//전체 주문 목록
	List<AdminOrderDTO> findAllOrderList(@Param("start") Integer start, @Param("end") Integer end);
	//전체주문 수
	int selectTotalOrdersCount();
	//주문대기 수
	int selectWaitingOrdersCount();
	//주문완료 수
	int selectCompleteOrdersCount();
	//단일주문ID조회
	int getOrderId(long orderId);
	//주문검색
	List<AdminOrderDTO> searchOrderListByKeyword(@Param("keyword") String keyword,
			@Param("orderState") Integer orderState, @Param("orderDate") String orderDate, @Param("start") int start,
			@Param("end") int end);
	//주문취소
	void updateCancleOrder(long orderId);
	//주문완료
	void updateCompleteOrder(long orderId);
	//단일 주문 목록조회
	AdminOrderDetailDTO selectOneOrderList(long orderId);
	//여러개 주문 목록조회
	List<AdminOrderDetailDTO> selectAllOrderList(long orderId);
	
	
	
	//전체 코디 목록
	List<AdminStaffDTO> selectAllCodiList();
	//전체 기사 목록
	List<AdminStaffDTO> selectAllDriverList();
	//단일 코디 목록
	AdminStaffDTO selectOneStaffList(long staffId);
	//단일코디ID조회
	int getStaffId(long staffId);
	//단건 주문상세번호 조회
	int getOrderDetailId(long orderDetailId);
	//일정이있는직원조회
	List<AdminScheduleStaffDTO> selectListScheduleStaff(long orderId);
	//일정삭제
	void deleteSchedule(long orderId, long staffId);
	//직원배정완료
	void updateStaffStatus(long staffId);
	
	int getNextMaxOrderDetailId();
	
	// 판매랭킹
    List<SoldRankDTO> getSoldRankProductWithoutImage(int orderState);

    List<SoldRankDTO> getSoldRankProductWithImage(int orderState);
}
