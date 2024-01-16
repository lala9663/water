package com.meta.metaway.staff.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.model.Staff;

@Repository
@Mapper
public interface IStaffRepository {
    Long getIdByAccount(String account);
        
//    void checkIfUserIsCodiOrDriver(Long id);

    Long getUserIdByAccount(String account);
    
	long selectStaffMaxNo();
	

	//회워 주문 목록 리스트2
	List<StaffListDTO> selectOrderProductList();
	List<Product> getProductForStaff(Long userId);

    String getUserAuthority(long userId);

    void createWorkPlace(Staff staff);
    
    void updateWorkPlace(Staff staff);

    String getWorkPlaceByUserId(long userId);
    
    Staff getStaffByUserId(long userId);
}
