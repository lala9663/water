package com.meta.metaway.staff.service;

import java.util.List;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.model.Staff;

public interface IStaffService {
    boolean isCodiOrDriver(String account);
    Long getUserIdByAccount(String account);
    
    //담당 회원 주문 목록 조회
	List<StaffListDTO> getOrderProductList();
	List<Product> getProductForStaff(String account);
	
    String getUserAuthority(int userId);

    void createWorkPlace(long userId, String workPlace);
    
    void updateWorkPlace(long userId, String workPlace);

    String getCurrentWorkPlace(long userId);
}
