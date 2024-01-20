package com.meta.metaway.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.meta.metaway.admin.dao.IAdminReturnRepository;
import com.meta.metaway.admin.dto.AdminReturnDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdminReturnServiceTest {

	@Mock
	private IAdminReturnRepository adminReturnRepository;

	@InjectMocks
	private AdminReturnService adminReturnService;

	@Test
	@DisplayName("전체 반납 수 조회")
	public void testSelectTotalReturnCount() {
		// given
		given(adminReturnRepository.selectTotalReturnCount()).willReturn(4);
		int totalReturnCount = adminReturnRepository.selectTotalReturnCount();
		System.out.println("totalReturnCount : " + totalReturnCount);
		// then
		assertEquals(4, totalReturnCount, "총 반납수는 4인가요 ");
	}

	@Test
	@DisplayName("전체 반납 목록 조회")
	public void testSelectAllReturnList() {
		// Given
		int page = 1;
		// When
		List<AdminReturnDTO> returnList = adminReturnService.selectAllReturnList(page);
		System.out.println(returnList);
		// Then
		assertNotNull(returnList);
	}

	@Test
	@DisplayName("단일 반납 수 조회")
	public void testSelectReturnCount() {
		// given
		given(adminReturnRepository.selectReturnCount()).willReturn(3);
		// when
		int returnCount = adminReturnRepository.selectReturnCount();
		System.out.println(returnCount);
		// then
		assertEquals(3, returnCount, " 반납수 확인 ");
	}

	@Test
	@DisplayName("총 해지 신청 수")
	public void testSelectTerminateCount() {
		// given
		given(adminReturnRepository.selectTerminateCount()).willReturn(2);
		// when
		int terminateCount = adminReturnRepository.selectTerminateCount();
		System.out.println(terminateCount);
		// then
		assertEquals(2, terminateCount, "총 해지 신청수는 확인");
	}

//
	@Test
	@DisplayName("총 환불 신청 수")
	public void testSelectRefundCount() {
		given(adminReturnRepository.selectRefundCount()).willReturn(77);
		int refundCount = adminReturnRepository.selectRefundCount();
		System.out.println("refundCount: " + refundCount);
		assertEquals(77, refundCount, "총 환불 신청수 확인");
	}

	@Test
	@DisplayName("주문상세번호가져오기")
	public void testGetOrderDetailIdByReturn() {
		long returnId = 1L;
		given(adminReturnRepository.getOrderDetailIdByReturn(returnId)).willReturn(235);
		int orderDetailId = adminReturnService.getOrderDetailIdByReturn(returnId);
		assertEquals(235, orderDetailId, "주문 상세번호가 235");
		verify(adminReturnRepository, times(1)).getOrderDetailIdByReturn(returnId);
	}

	@Test
	@DisplayName("반납번호 가져오기")
	public void testGetReturnId() {
		// given
		long returnId = 1L;
		given(adminReturnRepository.getReturnId(returnId)).willReturn(123); // Mock에서 반환되어야 하는 값 설정

		// when
		int actualReturnId = adminReturnService.getReturnId(returnId);

		// then
		assertEquals(123, actualReturnId, "반납번호가 123이어야 합니다.");
		verify(adminReturnRepository).getReturnId(returnId); // getReturnId 메서드가 한 번 호출되었는지 확인
	}

	@Test
	@DisplayName("전체 반납 상세 목록 조회")
	public void testSelectAllReturnDetailList() {
		// given
		long orderDetailId = 1L;
		given(adminReturnRepository.selectAllReturnDetailList(orderDetailId)).willReturn(createMockReturnDetailList());

		// when
		List<AdminReturnDTO> returnDetailList = adminReturnService.selectAllReturnDetailList(orderDetailId);

		// then
		assertNotNull(returnDetailList, "반납 상세 목록은 null이 아니어야 합니다.");
	}

	@Test
	@DisplayName("반납 직원 일정 목록 조회")
	public void testSelectListReturnScheduleStaff() {
		// given
		long orderDetailId = 1L;
		given(adminReturnRepository.selectListReturnScheduleStaff(orderDetailId))
				.willReturn(createMockScheduleStaffList());

		// when
		List<AdminScheduleStaffDTO> scheduleStaffList = adminReturnService.selectListReturnScheduleStaff(orderDetailId);

		// then
		assertEquals(2, scheduleStaffList.size(), "반납 직원 일정 목록 크기는 2여야 합니다.");
		// 추가적인 검증 로직을 구현하면 됩니다.
	}

	private List<AdminScheduleStaffDTO> createMockScheduleStaffList() {
		AdminScheduleStaffDTO staff1 = new AdminScheduleStaffDTO(1L, LocalDateTime.now(), 1, 0, 0, 0, 0, 0,
				"ROLE_DRIVER", "John Doe", null);
		AdminScheduleStaffDTO staff2 = new AdminScheduleStaffDTO(2L, LocalDateTime.now(), 2, 0, 0, 0, 0, 0,
				"ROLE_DRIVER", "Jane Doe", null);

		return Arrays.asList(staff1, staff2);
	}

	private List<AdminReturnDTO> createMockReturnDetailList() {
		AdminReturnDTO returnDetail1 = new AdminReturnDTO(1, LocalDateTime.now(), 10000, "반납신청1", 1);
		AdminReturnDTO returnDetail2 = new AdminReturnDTO(2, LocalDateTime.now(), 15000, "반납신청2", 2);
		AdminReturnDTO returnDetail3 = new AdminReturnDTO(2, LocalDateTime.now(), 20000, "반납신청3", 3);

		return Arrays.asList(returnDetail1, returnDetail2, returnDetail3);
	}
}
