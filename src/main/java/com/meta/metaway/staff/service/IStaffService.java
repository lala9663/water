package com.meta.metaway.staff.service;

import java.sql.Date;
import java.util.List;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.model.Staff;

public interface IStaffService {
    boolean isCodiOrDriver(String account);
    void createWorkPlace(String account, String workPlace);
    void updateWorkPlace(String account, Staff staff);
    Long getUserIdByAccount(String account);
    
    //담당 회원 주문 목록 조회
	List<StaffListDTO> getOrderProductList();
	List<Product> getProductForStaff(String account);
	

}
