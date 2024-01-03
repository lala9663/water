package com.meta.metaway.user.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.metaway.user.dao.IUserRepository;
import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.User;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
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
    @Transactional
    public User updateUser(User user) {
        User existingUser = userRepository.findByInfo(user.getAccount());
        
        if (existingUser != null && existingUser.getAccount().equals(user.getAccount())) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setAge(user.getAge());
            existingUser.setAddress(user.getAddress());

            userRepository.updateUser(existingUser);
            return existingUser;
        } else {
            // 사용자 정보가 올바르지 않은 경우 처리
            return null;
        }
    }
    
    @Override
    @Transactional
    public void deleteUserByAccount(String account) {
        userRepository.deleteUserByAccount(account);
    }



}
