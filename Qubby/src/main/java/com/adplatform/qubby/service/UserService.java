package com.adplatform.qubby.service;

import com.adplatform.qubby.model.User;
import com.adplatform.qubby.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  // 标记为Service，交给Spring管理（IOC）
@Transactional  // 自动事务管理
public class UserService {

    @Autowired  // IOC注入Repository
    private UserRepository userRepository;

    // 用户注册
    public User register(User user) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }
        return userRepository.save(user);  // JPA自动保存
    }

    // 用户登录
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }
}