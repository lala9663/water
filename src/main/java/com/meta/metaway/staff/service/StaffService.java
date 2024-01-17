package com.meta.metaway.staff.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.schedule.model.Schedule;
import com.meta.metaway.staff.dao.IStaffRepository;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.dto.StaffScheduleDTO;
import com.meta.metaway.staff.model.Staff;

@Service
public class StaffService implements IStaffService {

	@Autowired
	IStaffRepository staffRepository;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public boolean isCodiOrDriver(String account) {
		Long id = staffRepository.getIdByAccount(account); // 사용자 이름으로부터 ID를 가져옵니다.

		String authority = staffRepository.getUserAuthority(id);
		return authority.equals("ROLE_CODI") || authority.equals("ROLE_DRIVER");
	}

	@Override
	public Long getUserIdByAccount(String account) {
		return staffRepository.getUserIdByAccount(account);
	}

	// staff 담당된 회원의 제품 목록 조회
	@Override
	public List<Product> getProductForStaff(String account) {
		Long userId = staffRepository.getIdByAccount(account);

		if (userId != null) {
			List<Product> productList = staffRepository.getProductForStaff(userId);
			return productList;
		} else {
			throw new IllegalArgumentException("유효하지 않은 사용자 계정입니다.");
		}
	}

	public List<StaffListDTO> getOrderProductList() {
		return staffRepository.selectOrderProductList();
	}

	@Override
	public String getUserAuthority(int userId) {
		return staffRepository.getUserAuthority(userId);
	}

	public void createWorkPlace(long userId, String workPlace) {
		long staffId = staffRepository.selectStaffMaxNo() + 1;
		if (userId != 0) {
			Staff staff = new Staff();
			staff.setStaffId(staffId);
			staff.setUserId(userId);
			staff.setWorkPlace(workPlace);
			staffRepository.createWorkPlace(staff);
		} else {
			throw new IllegalArgumentException("CODI 또는 DRIVER 권한이 없습니다.");
		}
	}

	@Override
	public void updateWorkPlace(long userId, String workPlace) {

		Staff staff = staffRepository.getStaffByUserId(userId);

		System.out.println("스태프 컨트롤러: " + staff.toString());
		if (staff != null) {
			staff.setUserId(userId);
			staff.setWorkPlace(workPlace);
			staffRepository.updateWorkPlace(staff);
		} else {
			throw new IllegalArgumentException("해당 userId에 해당하는 스태프를 찾을 수 없습니다.");
		}
	}

	@Override
	public String getCurrentWorkPlace(long userId) {

		String workPlace = staffRepository.getWorkPlaceByUserId(userId);

		return workPlace;
	}

	// yoon----------
	@Override
	public List<StaffScheduleDTO> getDriverTodoList(long userId) {

		return staffRepository.getDriverTodoList(userId);
	}

	@Override
	@Transactional
	public void driverDatePick(StaffScheduleDTO staffSchedule) {
		staffRepository.driverDatePick(staffSchedule);
	}

	@Override
	public void settingScheduleDate(StaffScheduleDTO staff) {
		
		staff.setRequestDate(LocalDateTime.of(staff.getRequestDate().toLocalDate(), staff.getVisitTime()));
		staffRepository.driverDatePick(staff);
		if (staff.getVisitType() == 2) {
			if (staff.getStateType() == 2) {
				staff.setStateType(5);
			} else if (staff.getStateType() == 3) {
				staff.setStateType(6);
			} else if (staff.getStateType() == 4) {
				staff.setStateType(7);
			}
			staffRepository.settingScheduleDateByorderDetailId(staff);
		}
	}

	@Override
	public void completeSchedule(StaffScheduleDTO staff) {
		staffRepository.updateScheduleState(staff);
		if (staff.getVisitType() == 0) {
			staffRepository.updateOrderState(staff);
			Contract contract = staffRepository.getOrderDetailContractYear(staff);
			contract.setStartDate(LocalDate.now());
			contract.setEndDate(contract.getStartDate().plusYears(contract.getContractYear()));
			staffRepository.updateOrderDetailDate(contract);
		}
		else if(staff.getVisitType() == 2) {
			if (staff.getStateType() == 5) {
				staff.setStateType(8);
			} else if (staff.getStateType() == 6) {
				staff.setStateType(9);
			} else if (staff.getStateType() == 7) {
				staff.setStateType(10);
			}
			staffRepository.updateOrderDetailState(staff);
		}
	}

}
