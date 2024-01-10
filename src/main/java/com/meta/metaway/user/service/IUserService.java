package com.meta.metaway.user.service;

import java.util.List;

import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.Basket;
import com.meta.metaway.user.model.User;

public interface IUserService {

    boolean checkIfUserExists(String account);

    void joinProcess(JoinDTO joinDTO);
    
    User getUserByUsername(String username);
    
    User updateUser(String account, User user);
    
    void deleteUserById(Long id);
    
    boolean checkPassword(Long id, String enteredPassword);
    
    List<Basket> getBasketItemsByUserId(Long userId);
    
    void addProductToBasket(Basket basket) throws Exception;
    
    void removeProductFromBasket(String account, Basket basket);
}
