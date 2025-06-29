package com.example.demo.service.impl;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.domain.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    //    private final userAuthorityRepository userAuthorityRepository;
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.userAuthorityRepository=userAuthorityRepository;
//        System.out.println(userRepository);
    }

    //    @Autowired
//    private AuthorityRepository authorityRepository;
    @Override
    public void registerUser(User user) {
//
        String rawPassword = user.getPassword(); // 获取用户输入的原始密码
//        System.out.println(rawPassword);
        String encodedPassword = passwordEncoder.encode(rawPassword); // 加密密码
        user.setPassword(encodedPassword); // 设置加密后的密码
        userRepository.save(user); // 存储用户到数据库

    }
}
