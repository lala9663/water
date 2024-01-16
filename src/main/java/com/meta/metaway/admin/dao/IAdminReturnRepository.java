package com.meta.metaway.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.admin.dto.AdminReturnDTO;

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
	List<AdminReturnDTO> selectAllReturnList();
	// 반납 취소?
	void cancelReturn(long orderDetailId);
	// 반납 상세
	List<AdminReturnDTO> selectAllReturnDetailList(long orderDetailId);
}
