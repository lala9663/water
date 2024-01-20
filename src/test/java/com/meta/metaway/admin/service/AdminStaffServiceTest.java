package com.meta.metaway.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meta.metaway.admin.dao.IAdminStaffRepository;
import com.meta.metaway.admin.dto.AdminStaffDTO;

@ExtendWith(MockitoExtension.class)
class AdminStaffServiceTest {
	@Mock
	private IAdminStaffRepository adminStaffRepository;
	@InjectMocks
	private AdminStaffService adminStaffService;

	private AdminStaffDTO adminStaffDTO;
	private List<AdminStaffDTO> staffList;

	@BeforeEach
	void setUp() {
		adminStaffDTO = new AdminStaffDTO();
		staffList = Arrays.asList(adminStaffDTO);
	}

	@DisplayName("페이지별 직원 목록 조회")
	@Test
	public void testFindAllStaffList() {
		int page = 1;
		when(adminStaffRepository.selectAllStaff(anyInt(), anyInt())).thenReturn(staffList);

		List<AdminStaffDTO> result = adminStaffService.findAllStaffList(page);

		assertEquals(staffList, result);
		verify(adminStaffRepository).selectAllStaff(anyInt(), anyInt());
	}

	@DisplayName("전체 직원 목록 조회")
	@Test
	public void testSelectTotalStaffCount() {
		when(adminStaffRepository.selectTotalStaffCount()).thenReturn(10);

        int count = adminStaffService.selectTotalStaffCount();

        assertEquals(10, count);
        verify(adminStaffRepository).selectTotalStaffCount();
	}

	@DisplayName("직원 검색")
	@Test
	public void testSearchAllStaff() {
		String keyword = "test";
		String authorityName = "ROLE_DRIVER";
		int page = 1;
		when(adminStaffRepository.searchAllStaff(anyString(), anyString(), anyInt(), anyInt())).thenReturn(staffList);

		List<AdminStaffDTO> result = adminStaffService.searchAllStaff(keyword, authorityName, page);

		assertEquals(staffList, result);
		verify(adminStaffRepository).searchAllStaff(anyString(), anyString(), anyInt(), anyInt());
	}

	@DisplayName("직원 삭제")
	@Test
	public void testDeleteStaff() {
		long staffId = 1L;
		doNothing().when(adminStaffRepository).deleteStaff(staffId);

		adminStaffService.deleteStaff(staffId);

		verify(adminStaffRepository).deleteStaff(staffId);
	}

	@DisplayName("직원의 스태프ID 조회")
	@Test
	public void testGetStaffId() {
		long staffId = 1L;
		when(adminStaffRepository.getStaffId(staffId)).thenReturn(1);

		int result = adminStaffService.getStaffId(staffId);

		assertEquals(1, result);
		verify(adminStaffRepository).getStaffId(staffId);
	}

}
