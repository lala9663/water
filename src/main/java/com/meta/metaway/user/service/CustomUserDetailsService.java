package com.meta.metaway.user.service;

import org.springframework.security.core.userdetails.UserDetails;
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
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
    	    	
        //DB에서 조회
        User userData = userRepository.findByAccount(account);
        
        if (userData != null) {
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
           return new CustomUserDetails(userData);
        }
        return null;
    }
}

