package com.propro.service;

import com.propro.secondary.repository.EmployeeRepository;
import com.propro.primary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    public void doSomething() {
        // Работа с первой базой данных
        employeeRepository.findAll();

        // Работа со второй базой данных
        userRepository.findAll();
    }
}
