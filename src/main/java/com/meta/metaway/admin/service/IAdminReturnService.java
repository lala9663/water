package com.meta.metaway.admin.service;

import java.util.List;

import com.meta.metaway.admin.dto.AdminReturnDTO;

public interface IAdminReturnService {
	int selectTotalReturnCount();
	
	List<AdminReturnDTO> selectAllReturnList();
	// 총 반납 신청 수
	int selectReturnCount();
	// 총 해지 신청 수
	int selectTerminateCount();
	// 총 환불 신청 수
	int selectRefundCount();
	// 주문 상세번호 단일조회
	int getOrderDetailId(long orderDetailId);
	// 반납번호 단일조회
	int getReturnId(long returnId);
	
	
}
