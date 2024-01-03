package com.meta.metaway.user.service;

import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.User;

public interface IUserService {

    boolean checkIfUserExists(String account);

    void joinProcess(JoinDTO joinDTO);
    
    User getUserByUsername(String username);
    User updateUser(User user);
//    void deleteUserByAccount(String account);

}
