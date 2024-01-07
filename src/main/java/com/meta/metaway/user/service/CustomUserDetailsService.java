package com.meta.metaway.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meta.metaway.user.dao.IUserRepository;
import com.meta.metaway.user.dto.CustomUserDetails;
import com.meta.metaway.user.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
    	System.out.println("왜 커스텀 유저 디테일에서..");
    	System.out.println("account" + 	account);
        //DB에서 조회
        User userData = userRepository.findByAccount(account);
        System.out.println("커스텀 유저1: " + userData.getAccount());
        System.out.println("커스텀 비밀번호1: " + userData.getPassword());
        System.out.println(userData.toString());

        if (userData.getAccount() != null) {
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
           return new CustomUserDetails(userData);
        }
        return null;
    }
}

