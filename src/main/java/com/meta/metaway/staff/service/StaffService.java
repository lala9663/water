package com.meta.metaway.staff.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.staff.dao.IStaffRepository;
import com.meta.metaway.staff.model.Staff;

@Service
public class StaffService implements IStaffService{

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
    public void createWorkPlace(String account, String workplace) {
    	
    	Staff staff = new Staff();
    	
    	Long staffId = staffRepository.selectStaffMaxNo()+1;

        if (isCodiOrDriver(account)) {
            Long userId = staffRepository.getIdByAccount(account);
        	staff.setStaffId(staffId);
            staff.setUserId(userId);
            staff.setWorkPlace(workplace);
            
            staffRepository.createWorkPlace(staff);
        }    	    	
    }

    @Override
    public void updateWorkPlace(String account, Staff staff) {
        Long id = staffRepository.getUserIdByAccount(account);
        if (id != null) {
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("id", id);
            updateParams.put("workPlace", staff.getWorkPlace());
            
            staffRepository.updateWorkPlace(updateParams);
        } else {
            throw new IllegalArgumentException("유효하지 않은 사용자 계정입니다.");
        }
    }

	@Override
	public Long getUserIdByAccount(String account) {
	    return staffRepository.getUserIdByAccount(account);
	}

}
