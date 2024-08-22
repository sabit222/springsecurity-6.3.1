package com.propro.service;

import com.propro.primary.model.AppUser;
import com.propro.primary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password)); // Кодирование пароля
        appUser.setRoles(Collections.singleton("ROLE_USER")); // Присвоение роли
        appUser.setEnabled(true); // Активировать пользователя

        userRepository.save(appUser);
    }
}