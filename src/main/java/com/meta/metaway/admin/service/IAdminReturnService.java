package com.meta.metaway.admin.service;

import java.util.List;

import com.meta.metaway.admin.dto.AdminReturnDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;

public interface IAdminReturnService {
	int selectTotalReturnCount();
	
	List<AdminReturnDTO> selectAllReturnList(int page);
	// 총 반납 신청 수
	int selectReturnCount();
	// 총 해지 신청 수
	int selectTerminateCount();
	// 총 환불 신청 수
	int selectRefundCount();
	// 주문상세번호 단일 조회
	int getOrderDetailIdByReturn(long returnId);
	// 반납번호 단일조회
	int getReturnId(long returnId);
	// 반납 상세
	List<AdminReturnDTO> selectAllReturnDetailList(long orderDetailId);
	// 반납 직원 리스트
	List<AdminScheduleStaffDTO> selectListReturnScheduleStaff(long orderDetailId);
}
