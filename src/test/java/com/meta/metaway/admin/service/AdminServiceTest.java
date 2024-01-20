package com.meta.metaway.admin.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.dto.SoldRankDTO;
import com.meta.metaway.admin.dto.UserCountDTO;
import com.meta.metaway.admin.model.Visitor;
import com.meta.metaway.staff.dto.StaffDTO;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
	@Mock
	private IAdminRepository adminRepository;
	@InjectMocks
	private AdminService adminService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	public static final String DAILY_VISITOR_COUNT_KEY = "daily_visitor_count:";

	@Mock
	private RedisTemplate<String, Long> redisTemplate;

	@MockBean
	private ValueOperations<String, Long> valueOperations;

	@Test
	@DisplayName("전체 주문 조회")
	public void testFindAllOrderList() {
		// given
		given(adminRepository.findAllOrderList(1, 10)).willReturn(createMockAdminOrderList());
		// when
		List<AdminOrderDTO> orderList = adminService.findAllOrderList(1);
		// then
		assertEquals(2, orderList.size(), "The total order count should be 2.");
	}

	// 2
	@Test
	@DisplayName("전체 주문 수 조회")
	public void testSelectTotalOrdersCount() {
		// given
		given(adminRepository.selectTotalOrdersCount()).willReturn(10);

		// when
		int totalOrdersCount = adminService.selectTotalOrdersCount();

		// then
		assertEquals(10, totalOrdersCount, "The total orders count should be 10.");
	}

	// 3
	@Test
	@DisplayName("전체 주문 대기 수 조회")
	public void testSelectWaitingOrdersCount() {
		// Given
		given(adminRepository.selectWaitingOrdersCount()).willReturn(10);
		// When
		int waitingOrdersCount = adminService.selectWaitingOrdersCount();
		// Then
		assertEquals(10, waitingOrdersCount, "The waiting orders count should be 10.");
	}

	// 4
	@Test
	@DisplayName("키워드로 검색")
	public void testSearchOrderListByKeyword() {
		// Given
		String keyword = "example";
		Integer orderState = 0;
		String orderDate = LocalDate.now().toString();
		int start = 1;
		int end = 10;
		// 예상되는 반환 값 설정
		// 예상되는 반환 값 설정
		given(adminRepository.searchOrderListByKeyword("%" + keyword + "%", orderState, orderDate, start, end))
				.willReturn(createMockAdminOrderList());

		// When
		List<AdminOrderDTO> searchResult = adminService.searchOrderListByKeyword(keyword, orderState, orderDate, 1);

		// Then
		assertNotNull(searchResult, "Search result should not be null.");
	}

	@Test
	@DisplayName("주문 취소 업데이트")
	public void testUpdateCancelOrder() {
		// Given
		long orderId = 1L;
		// When
		adminRepository.updateCancleOrder(orderId);
		// Then
		verify(adminRepository).updateCancleOrder(orderId);
	}

	@Test
	@DisplayName("완료된 주문 개수 조회")
	public void testSelectCompleteOrdersCount() {
		// Given
		given(adminRepository.selectCompleteOrdersCount()).willReturn(5);

		// When
		int completeOrdersCount = adminService.selectCompleteOrdersCount();

		// Then
		assertEquals(5, completeOrdersCount, "The complete orders count should be 5.");
	}

	@Test
	@DisplayName("주문 ID 조회")
	public void testGetOrderId() {
		long orderId = 1L;
		given(adminRepository.getOrderId(orderId)).willReturn((int) orderId);
		// When
		long resultOrderId = adminService.getOrderId(orderId);
		// Then
		assertEquals(orderId, resultOrderId, "The returned order ID should be 1L.");
	}

	@Test
	@DisplayName("단일 주문 조회")
	public void testSelectOneOrderList() {
		// Given
		long orderId = 1L;
		given(adminRepository.selectOneOrderList(orderId)).willReturn(createMockAdminOrder());
		// When
		AdminOrderDetailDTO order = adminService.selectOneOrderList(orderId);
		// Then
		assertNotNull(order, "The returned order should not be null.");
		assertEquals(orderId, order.getOrderId(), "The order ID should match.");
		// Add additional assertions based on your specific use case.
	}

	@Test
	@DisplayName("주문 완료 업데이트")
	public void testUpdateCompleteOrder() {
		// Given
		long orderId = 1L;

		// When
		adminRepository.updateCompleteOrder(orderId);

		// Then
		verify(adminRepository).updateCompleteOrder(orderId);
	}

	@Test
	@DisplayName("전체 코디 목록 조회")
	public void testSelectAllCodiList() {
		// Given
		given(adminRepository.selectAllCodiList()).willReturn(createMockCodiList());

		// When
		List<AdminStaffDTO> codiList = adminService.selectAllCodiList();

		// Then
		assertNotNull(codiList, "The returned codi list should not be null.");
	}

	@Test
	public void testSelectAllDriverList() {
		// Given
		given(adminRepository.selectAllCodiList()).willReturn(createMockDriverList());
		// When
		List<AdminStaffDTO> codiList = adminService.selectAllCodiList();
		// Then
		assertNotNull(codiList, "The returned codi list should not be null.");
	}

	@Test
	@DisplayName("스태프 ID로 주문 ID 조회")
	public void testGetOrderIdByStaffId() {
		// Given
		long staffId = 1L;
		given(adminRepository.getOrderIdByStaffId(staffId)).willReturn((int) 10L);

		// When
		long orderId = adminService.getOrderIdByStaffId(staffId);

		// Then
		assertEquals(10L, orderId, "The order ID should match.");
	}

	@Test
	@DisplayName("스태프의 스케줄 목록 조회")
	public void testSelectListScheduleStaff() {
		// Given
		long staffId = 1L;
		given(adminRepository.selectListScheduleStaff(staffId)).willReturn(createMockScheduleList());
		// When
		List<AdminScheduleStaffDTO> scheduleList = adminService.selectListScheduleStaff(staffId);
		// Then
		assertNotNull(scheduleList, "The returned schedule list should not be null.");
	}

	@Test
	@DisplayName("일정 삭제")
	public void testDeleteSchedule() {
		long scheduleId = 1L;
		long staffId = 1L;

		// When
		adminRepository.deleteSchedule(scheduleId, staffId);

		// Then
		verify(adminRepository).deleteSchedule(scheduleId, staffId);
	}

	@Test
	@DisplayName("전체 주문 목록 조회")
	public void testSelectAllOrderList() {
		// Given
		long orderId = 1L;
		given(adminRepository.selectAllOrderList(orderId)).willReturn(createMockOrderList());

		// When
		List<AdminOrderDetailDTO> orderList = adminService.selectAllOrderList(orderId);
		// Then
		assertNotNull(orderList, "The returned order list should not be null.");
		// Add additional assertions based on your specific use case.
		assertEquals(2, orderList.size(), "The size of the order list should be 2."); // Adjust the expected size based
																						// on your mock data.
		// Add more assertions based on the expected behavior of your service.
	}

//	@Test
//	@DisplayName("상품 이미지 없는 판매 랭킹 조회")
//	public void testGetSoldRankProductWithoutImage() {
//		int orderState = 0;
//	    // Given
//	    given(adminRepository.getSoldRankProductWithoutImage(orderState).willReturn(createMockSoldRankList());
//
//	    // When
//	    List<SoldRankDTO> soldRankList = adminService.getSoldRankProductWithoutImage(1);
//
//	    // Then
//	    assertNotNull(soldRankList, "The returned sold rank list should not be null.");
//	    // Add additional assertions based on your specific use case.
//	}
//
//	private List<SoldRankDTO> createMockSoldRankList() {
//	    SoldRankDTO soldRank1 = new SoldRankDTO();
//	    soldRank1.setProductId(1L);
//	    soldRank1.setProductName("Product 1");
//	    soldRank1.setTotalSoldCount(10);
//	    // Set other properties as needed
//
//	    SoldRankDTO soldRank2 = new SoldRankDTO();
//	    soldRank2.setProductId(2L);
//	    soldRank2.setProductName("Product 2");
//	    soldRank2.setTotalSoldCount(8);
//	    // Set other properties as needed
//
//	    return Arrays.asList(soldRank1, soldRank2);
//	}

//	@Test
//	@DisplayName("상품 이미지 포함 판매 랭킹 조회")
//	public void testGetSoldRankProductWithImage() {
//	    int orderState = 0;
//	    given(adminRepository.getSoldRankProductWithImage(orderState)).willReturn(
//	    		Arrays.asList(
//	    		        new SoldRankDTO("Product1", 10),
//	    		        new SoldRankDTO("Product2", 8),
//	    		        new SoldRankDTO("Product3", 5)
//	    		    ));
//	    // When
//	    List<SoldRankDTO> soldRankList = adminService.getSoldRankProductWithImage(orderState);
//
//	    // Then
//	    assertNotNull(soldRankList, "The returned sold rank list should not be null.");
//	    assertEquals(3, soldRankList.size(), "The size of the sold rank list should match.");
//
//	    assertEquals("Product1", soldRankList.get(0).getProductName(), "The product name should match.");
//	    assertEquals(10, soldRankList.get(0).getTotalSales(), "The total sales should match.");
//	}

	@Test
	@DisplayName("총 회원 수 조회")
	public void testGetTotalUser() {
		// Given
		UserCountDTO mockUserCount = new UserCountDTO();
		mockUserCount.setTotalAllCount(1000);
		given(adminRepository.getTotalUser()).willReturn(mockUserCount);

		// When
		UserCountDTO userCountDTO = adminService.getTotalUser();

		// Then
		assertNotNull(userCountDTO, "The returned user count should not be null.");
		assertEquals(1000, userCountDTO.getTotalAllCount(), "The total user count should match.");
	}

	@Test
	@DisplayName("총 판매 수 조회")
	public void testGetTotalSalesCount() {
		// Given
		given(adminRepository.getTotalSalesCount(1)).willReturn(50);
		// When
		int totalSalesCount = adminService.getTotalSalesCount(1);
		// Then
		assertEquals(50, totalSalesCount, "The returned total sales count should be 50.");
		// Add additional assertions based on your specific use case.
	}

//	@Test
//	@DisplayName("일일 방문자 수 증가")
//	public void testIncreaseDailyVisitorCount() {
//		// Given
//		String key = DAILY_VISITOR_COUNT_KEY + LocalDate.now();
//
//		// 가상의 Redis에서 조회한 일일 방문자 수를 설정
//		when(redisTemplate.opsForValue().increment(key)).thenReturn(10L);
//
//		// When
//		adminService.increaseDailyVisitorCount();
//
//		// Then: Redis에 저장된 값을 확인
//		verify(redisTemplate.opsForValue()).increment(key);
//	}

//
//	@Test
//	@DisplayName("일일 방문자 수 조회")
//	void testGetDailyVisitorCount() {
//	    // Given
//	    String key = DAILY_VISITOR_COUNT_KEY + LocalDate.now();
//
//	    // 가상의 Redis에서 조회한 일일 방문자 수를 설정
//	    when(redisTemplate.opsForValue().get(key)).thenReturn("10"); 
//
//	    // When
//	    long result = adminService.getDailyVisitorCount();
//
//	    // Then: 결과 검증
//	    assertEquals(10, result, "The returned daily visitor count should be 10.");
//	}


//	@Test //fail
//	@DisplayName("일일 방문자 수 초기화")
//	void testResetDailyVisitorCount() {
//		// Given
//		LocalDate today = LocalDate.now();
//		String key =DAILY_VISITOR_COUNT_KEY + today;
//		given(adminRepository.getVisitorCountByDate(today)).willReturn(10L);
//		System.out.println(key + "key");
//		// When
//		adminService.resetDailyVisitorCount(key);
//		System.out.println();
//		// Then
//		verify(adminRepository).getVisitorCountByDate(today);
//		verify(redisTemplate).delete(anyString());
//	}

	@Test // ok
	@DisplayName("특정 날짜의 방문자 수 조회")
	void testGetVisitorCountByDate() {
		// Given
		LocalDate date = LocalDate.of(2022, 1, 1);
		given(adminRepository.getVisitorCountByDate(date)).willReturn(20L);

		// When
		Long visitorCount = adminService.getVisitorCountByDate(date);

		// Then
		assertEquals(20L, visitorCount, "The returned visitor count should be 20.");
	}

	@Test
	@DisplayName("전체 방문자 수의 평균 조회")
	void testGetOverallAverageVisitorCount() {
	    // Given
	    List<Long> visitorCounts = Arrays.asList(10L, 20L, 30L, 40L, 50L);
	    given(adminRepository.getOverallAverageVisitorCount()).willReturn(30.0);

	    // When
	    Double averageVisitorCount = adminService.getOverallAverageVisitorCount();

	    // Then
	    assertEquals(30.0, averageVisitorCount, 0.001, "The returned average visitor count should be 30.0.");
	}

//	@Test
//	@DisplayName("방문자 수 기록 테스트")
//	void testInsertViewCount() {
//	    // Given
//		String key = DAILY_VISITOR_COUNT_KEY + LocalDate.now();
//	    long expectedMaxNo = 1L;
//	    Visitor mockVisitor = new Visitor();
//	    when(redisTemplate.opsForValue().get(key)).thenReturn(expectedMaxNo);
//
//	    // When
//	    adminService.insertViewCount(mockVisitor);
//
//	    // Then
//	    verify(adminRepository).insertViewCount(mockVisitor);
//	}


	@Test
	@DisplayName("대시보드 주문 목록 조회 테스트")
	void testGetDashboardOrderList() {
	    // Given
	    List<AdminOrderDTO> mockOrderList = Arrays.asList(
	            new AdminOrderDTO(),
	            new AdminOrderDTO()
	    );
	    when(adminRepository.dashBoardOrderList()).thenReturn(mockOrderList);

	    // When
	    List<AdminOrderDTO> resultOrderList = adminService.getDashboardOrderList();

	    // Then
	    assertEquals(mockOrderList.size(), resultOrderList.size(), "The size of the order list should match.");
	    for (int i = 0; i < mockOrderList.size(); i++) {
	        assertEquals(mockOrderList.get(i), resultOrderList.get(i), "The order details should match.");
	    }
	}

//
	@Test
	@DisplayName("코디를 포함한 사용자 목록 조회 테스트")
	void testGetUsersWithCodi() {
	    // Given
	    List<StaffDTO> mockCodiList = Arrays.asList(
	            new StaffDTO(),
	            new StaffDTO()
	    );
	    when(adminRepository.getUsersWithCodi()).thenReturn(mockCodiList);

	    // When
	    List<StaffDTO> resultCodiList = adminService.getUsersWithCodi();

	    // Then
	    assertEquals(mockCodiList.size(), resultCodiList.size(), "The size of the codi list should match.");
	    for (int i = 0; i < mockCodiList.size(); i++) {
	        assertEquals(mockCodiList.get(i), resultCodiList.get(i), "The codi details should match.");
	    }
	}

	@Test
	@DisplayName("이번 달의 총 주문 가격 조회 테스트")
	void testGetTotalOrderPriceMonth() {
	    // Given
	    long expectedTotalOrderPrice = 1000L; // Set your expected value
	    when(adminRepository.getTotalOrderPriceMonth()).thenReturn(expectedTotalOrderPrice);

	    // When
	    long resultTotalOrderPrice = adminService.getTotalOrderPriceMonth();

	    // Then
	    assertEquals(expectedTotalOrderPrice, resultTotalOrderPrice, "The total order price for this month should match.");
	}

	@Test
	@DisplayName("이번 달의 총 렌탈 가격 조회 테스트")
	void testGetTotalRentalPriceMonth() {
	    // Given
	    long expectedTotalRentalPrice = 500L; // Set your expected value
	    when(adminRepository.getTotalRentalPriceMonth()).thenReturn(expectedTotalRentalPrice);

	    // When
	    long resultTotalRentalPrice = adminService.getTotalRentalPriceMonth();

	    // Then
	    assertEquals(expectedTotalRentalPrice, resultTotalRentalPrice, "The total rental price for this month should match.");
	}
//
	@Test
	@DisplayName("반납배정삭제")
	public void testDeleteReturnSchedule() {
		// Given
		long orderDetailId = 1L;
		long staffId = 2L;

		// When
		adminService.deleteReturnSchedule(orderDetailId, staffId);

		// Then
		verify(adminRepository, times(1)).deleteReturnSchedule(orderDetailId, staffId);
	}

	@Test
    public void testUpdateCompleteOrderDetail() {
        // Given
        long orderDetailId = 1L;

        // When
        adminService.updateCompleteOrderDetail(orderDetailId);

        // Then
        verify(adminRepository, times(1)).updateCompleteOrderDetail(orderDetailId);
    }

	private List<AdminOrderDTO> createMockAdminOrderList() {
		AdminOrderDTO order1 = new AdminOrderDTO();
		order1.setOrderId(1L);

		AdminOrderDTO order2 = new AdminOrderDTO();
		order2.setOrderId(2L);

		return Arrays.asList(order1, order2);
	}

	private AdminOrderDetailDTO createMockAdminOrder() {
		AdminOrderDetailDTO order = new AdminOrderDetailDTO();
		order.setOrderId(1L);
		return order;
	}

	private List<AdminStaffDTO> createMockCodiList() {
		List<AdminStaffDTO> codiList = new ArrayList<>();

		// 가상의 코디 데이터 생성
		AdminStaffDTO codi1 = new AdminStaffDTO();
		codi1.setStaffId(1L);

		AdminStaffDTO codi2 = new AdminStaffDTO();
		codi2.setStaffId(2L);

		return codiList;
	}

	private List<AdminStaffDTO> createMockDriverList() {
		List<AdminStaffDTO> driverList = new ArrayList<>();

		// 가상의 운전자 데이터 생성
		AdminStaffDTO driver1 = new AdminStaffDTO();
		driver1.setStaffId(1L);

		AdminStaffDTO driver2 = new AdminStaffDTO();
		driver2.setStaffId(2L);
		driverList.add(driver1);
		driverList.add(driver2);
		return driverList;
	}

	private List<AdminScheduleStaffDTO> createMockScheduleList() {
		List<AdminScheduleStaffDTO> scheduleList = new ArrayList<>();

		// Create mock schedule data
		AdminScheduleStaffDTO schedule1 = new AdminScheduleStaffDTO();
		schedule1.setScheduleId(1L);
		schedule1.setStaffId(1L);

		AdminScheduleStaffDTO schedule2 = new AdminScheduleStaffDTO();
		schedule2.setScheduleId(2L);
		schedule2.setStaffId(1L);

		scheduleList.add(schedule1);
		scheduleList.add(schedule2);

		return scheduleList;
	}

	private List<AdminOrderDetailDTO> createMockOrderList() {
		AdminOrderDetailDTO order1 = new AdminOrderDetailDTO();
		order1.setOrderId(1L);
		// Set other properties as needed

		AdminOrderDetailDTO order2 = new AdminOrderDetailDTO();
		order2.setOrderId(2L);
		// Set other properties as needed

		return Arrays.asList(order1, order2);
	}

}
