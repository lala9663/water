package com.meta.metaway.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.order.model.Order;
import com.meta.metaway.order.model.OrderDetail;
import com.meta.metaway.user.model.User;

@Repository
@Mapper
public interface IUserRepository {
    Boolean existsByAccount(String account);

    User findByAccount(String account);
    
    Long findById(Long id);

    Long getUserIdByAccount(String account);
    
    void insertUser(User user);  
    
    void insertUserRole(User user);
		
    User findByInfo(String account);
    
    void updateUser(User user);

    void deleteUserById(Long id);
    
    String findPasswordById(Long id);
    
    List<Order> getOrderByUserId(Long id);
    
    List<OrderDetail> getOrderDetailByUserId(Long id);
    
    void updatePassword(User user);
    
    long selectUserMaxNo();

}
