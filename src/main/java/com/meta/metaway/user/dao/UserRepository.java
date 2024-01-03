package com.meta.metaway.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.user.model.User;

@Repository
@Mapper
public interface UserRepository {
    Boolean existsByAccount(String account);

    User findByAccount(String account);
    
    void insertUser(User user);    
    void insertUserRole(User user);
	long selectUserMaxNo();

}
