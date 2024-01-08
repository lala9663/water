package com.meta.metaway.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.meta.metaway.admin.dto.OrderDTO;
import com.meta.metaway.admin.dto.OrderDetailDTO;
import com.meta.metaway.admin.dto.StaffDTO;

@Repository
@Mapper
public interface IAdminRepository {
	List<OrderDTO> findAllOrderList(@Param("start") Integer start, @Param("end") Integer end);
	int selectTotalOrdersCount();
	int selectWaitingOrdersCount();
	int selectCompleteOrdersCount();
	int getOrderId(long orderId);
	List<OrderDTO> searchOrderListByKeyword(@Param("keyword")  String keyword, 
											@Param("orderState") Integer orderState, 
											@Param("orderDate") String orderDate,
											@Param("start") int start, @Param("end") int end);
	void updateCancleOrder(long orderId);
	void updateCompleteOrder(long orderId);
	OrderDetailDTO selectOneOrderList(long orderId);
	List<StaffDTO> selectAllCodiList();
	List<StaffDTO> selectAllDriverList();
	
	}
