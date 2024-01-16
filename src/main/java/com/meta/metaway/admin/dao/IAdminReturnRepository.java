package com.meta.metaway.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.meta.metaway.admin.dto.AdminReturnDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;

@Repository
@Mapper
public interface IAdminReturnRepository {
	// 총 반납 수
	int selectTotalReturnCount();
	// 총 반납 신청 수
	int selectReturnCount();
	// 총 해지 신청 수
	int selectTerminateCount();
	// 총 환불 신청 수
	int selectRefundCount();
	// 전체 반납리스트 조회
	List<AdminReturnDTO> selectAllReturnList(@Param("start") Integer start, @Param("end") Integer end);
	// 주문상세번호 단일 조회
	int getOrderDetailIdByReturn(long returnId);
	// 반납 번호 단일 조회
	int getReturnId(long returnId);
	// 반납 상세
	List<AdminReturnDTO> selectAllReturnDetailList(long orderDetailId);
	//
	List<AdminScheduleStaffDTO> selectListReturnScheduleStaff(long orderDetailId);
	
}
