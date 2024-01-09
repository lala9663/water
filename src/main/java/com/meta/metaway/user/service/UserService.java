package com.meta.metaway.user.service;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.metaway.user.dao.IBasketRepository;
import com.meta.metaway.user.dao.IUserRepository;
import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.Basket;
import com.meta.metaway.user.model.User;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IBasketRepository basketRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(IUserRepository userRepository, IBasketRepository basketRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public boolean checkIfUserExists(String account) {
        return userRepository.existsByAccount(account);
    }

    @Override
    @Transactional
    public void joinProcess(JoinDTO joinDTO) {
        String account = joinDTO.getAccount();
        boolean isExist = checkIfUserExists(account);

        if (!isExist) {
            User data = new User();
            User role = new User();

            Long id = userRepository.selectUserMaxNo() + 1;
            String password = joinDTO.getPassword();
            String email = joinDTO.getEmail();
            String name = joinDTO.getName();
            String phone = joinDTO.getPhone();
            int age = joinDTO.getAge();
            String address = joinDTO.getAddress();

            data.setId(id);
            data.setAccount(account);
            data.setPassword(bCryptPasswordEncoder.encode(password));
            data.setEmail(email);
            data.setName(name);
            data.setPhone(phone);
            data.setAge(age);
            data.setAddress(address);

            role.setId(id);
            if (account.contains("co")) {
                role.setAuthorities("ROLE_CODI");
            } else if (account.contains("dr")) {
                role.setAuthorities("ROLE_DRIVER");
            } else if (account.contains("ad")) {
                role.setAuthorities("ROLE_ADMIN");
            } else {
                role.setAuthorities("ROLE_USER");
            }

            userRepository.insertUser(data);
            userRepository.insertUserRole(role);
        } else {
            // 이미 존재하는 경우
        }
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
    	System.out.println("서비스 단에서 유저네임" + username);
        return userRepository.findByInfo(username);
    }

    @Override
    public User updateUser(String account, User user) {
        Long id = userRepository.getUserIdByAccount(account);
        
        if (id != null) {
            User existingUser = userRepository.findByInfo(account);
            if (existingUser != null) {
                String newPassword = user.getPassword(); 
                if (newPassword != null && !newPassword.isEmpty()) {
                    String encryptedPassword = bCryptPasswordEncoder.encode(newPassword); 
                    existingUser.setPassword(encryptedPassword); 
                }

                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPhone(user.getPhone());
                existingUser.setAge(user.getAge());
                existingUser.setAddress(user.getAddress());

                userRepository.updateUser(existingUser); 

                return existingUser;
            } else {
                throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }
    }

    @Override
    public List<Basket> getBasketItemsByUserId(Long userId) {
        return basketRepository.getBasketItemsByUserId(userId);
    }

    @Override
    public void addProductToBasket(String account, Long contractId, Basket basket) {
    	Long userId = userRepository.getUserIdByAccount(account);
    	Long productId = basketRepository.getProductIdByContractId(contractId);
    	
        basket.setProductId(productId);

        basketRepository.addProductToBasket(basket);
    }

    @Override
    public void removeProductFromBasket(String account, Basket basket) {
        basketRepository.removeProductFromBasket(basket);
    }



    
    @Override
    public boolean checkPasswordByAccount(String account, String password) {
        String encryptedPassword = userRepository.getPasswordByAccount(account);
        return encryptedPassword != null && bCryptPasswordEncoder.matches(password, encryptedPassword);
    }
    
    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }



}
