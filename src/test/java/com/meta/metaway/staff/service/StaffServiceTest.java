package com.meta.metaway.staff.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.staff.dao.IStaffRepository;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.dto.StaffScheduleDTO;
import com.meta.metaway.staff.model.Staff;

import io.jsonwebtoken.lang.Collections;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

	@Mock
	private IStaffRepository staffRepository;
	@InjectMocks
	private StaffService staffService;
	
	private StaffScheduleDTO staffScheduleDTO;
	
	@BeforeEach
    void setUp() {
        staffScheduleDTO = new StaffScheduleDTO();
        // 초기화 코드 및 필요한 세팅을 여기에 작성
    }

	@DisplayName("")
	@Test
	public void testIsCodiOrDriver() {
		String account = "user_account";
		when(staffRepository.getIdByAccount(account)).thenReturn(1L);
		when(staffRepository.getUserAuthority(1L)).thenReturn("ROLE_CODI");

		// Act
		boolean result = staffService.isCodiOrDriver(account);

		// Assert
		assertEquals(true, result);
	}
	@DisplayName("아이디 찾기")
	@Test
	public void testGetUserIdByAccount() {
		// Arrange
		String account = "user_account";
		when(staffRepository.getUserIdByAccount(account)).thenReturn(1L);

		// Act
		Long userId = staffService.getUserIdByAccount(account);

		// Assert
		assertEquals(1L, userId);
	}
	@DisplayName("직원 배정 상품 조회")
	@Test
	public void testGetProductForStaff() {
		String account = "user_account";
		Long userId = 1L;
		when(staffRepository.getIdByAccount(account)).thenReturn(userId);
		when(staffRepository.getProductForStaff(userId)).thenReturn(Collections.emptyList());

		// Act
		List<Product> productList = staffService.getProductForStaff(account);

		// Assert
		assertEquals(0, productList.size());
	}
	
	@DisplayName("주문 상품 조회")
	@Test
	public void testGetOrderProductList() {
		List<StaffListDTO> expectedList = Collections.emptyList();
		when(staffRepository.selectOrderProductList()).thenReturn(expectedList);

		// Act
		List<StaffListDTO> productList = staffService.getOrderProductList();

		// Assert
		assertEquals(expectedList, productList);
	}
	@DisplayName("유저 권한 조회")
	@Test
	public void testGetUserAuthority() {
		long userId = 1L;
		when(staffRepository.getUserAuthority(userId)).thenReturn("ROLE_DRIVER");

		// Act
		String authority = staffService.getUserAuthority(userId);

		// Assert
		assertEquals("ROLE_DRIVER", authority);
	}
	@DisplayName("직원 근무지 생성")
	@Test
	public void testCreateWorkPlace() {
		long userId = 1L;
		String workPlace = "Workplace A";
		when(staffRepository.selectStaffMaxNo()).thenReturn(10L);
		doNothing().when(staffRepository).createWorkPlace(any());

		// Act
		staffService.createWorkPlace(userId, workPlace);
	}
	@DisplayName("직원 근무지 변경")
	@Test
	public void testUpdateWorkPlace() {
		long userId = 1L;
		String workPlace = "Updated Workplace";
		when(staffRepository.getStaffByUserId(userId)).thenReturn(new Staff());
		doNothing().when(staffRepository).updateWorkPlace(any());

		// Act
		staffService.updateWorkPlace(userId, workPlace);
	}

	@DisplayName("근무지 조회")
	@Test
	public void testGetCurrentWorkPlace() {
		long userId = 1L;
		when(staffRepository.getWorkPlaceByUserId(userId)).thenReturn("Current Workplace");

		// Act
		String workPlace = staffService.getCurrentWorkPlace(userId);

		// Assert
		assertEquals("Current Workplace", workPlace);
	}
	@DisplayName("드라이버 업무 조회")
	@Test
	public void testGetDriverTodoList() {
		StaffScheduleDTO staffScheduleDTO = new StaffScheduleDTO();
		staffScheduleDTO.setScheduleId(1L);

		// Mock repository 설정
		when(staffRepository.getDriverTodoList(1L)).thenReturn(Arrays.asList(staffScheduleDTO));

		// 테스트할 메서드 호출
		List<StaffScheduleDTO> result = staffService.getDriverTodoList(1L);

		// 결과 검증
		assertEquals(1, result.size());
		StaffScheduleDTO retrievedDTO = result.get(0);
		assertEquals(1L, retrievedDTO.getScheduleId());

		// staffRepository.getDriverTodoList(1L)가 한 번 호출되었는지 검증
		verify(staffRepository, times(1)).getDriverTodoList(1L);
	}
	
	@DisplayName("드라이버 시간 배정")
	@Test
	public void testDriverDatePick() {
		StaffScheduleDTO staffSchedule = new StaffScheduleDTO();
		staffSchedule.setScheduleId(1L);
		staffSchedule.setRequestDate(LocalDateTime.now());
		doNothing().when(staffRepository).driverDatePick(any());

		// Act
		staffService.driverDatePick(staffSchedule);
	}

	@DisplayName("배송예정일 입력")
	@Test
	public void testSettingScheduleDate() {
		StaffScheduleDTO staffScheduleDTO = new StaffScheduleDTO();
		staffScheduleDTO.setScheduleId(1L);
		staffScheduleDTO.setRequestDate(LocalDate.of(2022, 1, 1).atTime(LocalTime.NOON));

		// Mock repository 설정
		when(staffRepository.getDriverTodoList(1L)).thenReturn(Arrays.asList(staffScheduleDTO));

		// 테스트할 메서드 호출
		List<StaffScheduleDTO> result = staffService.getDriverTodoList(1L);

		// 결과 검증
		assertEquals(1, result.size());
		StaffScheduleDTO retrievedDTO = result.get(0);
		assertEquals(1L, retrievedDTO.getScheduleId());
		assertEquals(LocalDate.of(2022, 1, 1).atTime(LocalTime.NOON), retrievedDTO.getRequestDate());

		// staffRepository.getDriverTodoList(1L)가 한 번 호출되었는지 검증
		verify(staffRepository, times(1)).getDriverTodoList(1L);
	}
	@DisplayName("스케쥴 완료 처리")
	@Test
	public void testCompleteSchedule() { // 예시 값
		Contract mockContract = new Contract();
	    mockContract.setStartDate(LocalDate.now());
	    mockContract.setContractYear(1);  // 예시 값

	    // staffRepository.getOrderDetailContractYear 호출 시 mockContract 반환하도록 설정
	    Mockito.when(staffRepository.getOrderDetailContractYear(any(StaffScheduleDTO.class)))
	           .thenReturn(mockContract);

	    // 테스트 실행
	    staffService.completeSchedule(staffScheduleDTO);
	}
	@DisplayName("코디 업무 조회")
	@Test
	public void testGetCodyTodoList() {
		StaffScheduleDTO staffScheduleDTO = new StaffScheduleDTO();
		staffScheduleDTO.setScheduleId(1L);

		// Mock repository 설정
		when(staffRepository.getCodyTodoList(1L)).thenReturn(Arrays.asList(staffScheduleDTO));

		// 테스트할 메서드 호출
		List<StaffScheduleDTO> result = staffService.getCodyTodoList(1L);

		// 결과 검증
		assertEquals(1, result.size());
		StaffScheduleDTO retrievedDTO = result.get(0);
		assertEquals(1L, retrievedDTO.getScheduleId());

		// staffRepository.getCodyTodoList(1L)가 한 번 호출되었는지 검증
		verify(staffRepository, times(1)).getCodyTodoList(1L);
	}
	@DisplayName("코디 업무 완료 처리")
	@Test
	public void testCompleteCodiSchedule() {
		staffService.completeCodiSchedule(staffScheduleDTO);
	    verify(staffRepository, times(1)).updateScheduleState(staffScheduleDTO);
	}

}
