package com.propro.controller;

import com.propro.secondary.model.Employee;
import com.propro.service.CurrencyService;
import com.propro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CurrencyService currencyService;


    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("baseCurrency", "USD");
        model.addAttribute("amount", 0.0);
        model.addAttribute("result", 0.0);
        return "index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_form";
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employee";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employee_form";
        } else {
            return "redirect:/employee";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        if (employeeService.getEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
        }
        return "redirect:/employee";
    }
    @PostMapping("/convert")
    public String convert(@RequestParam String baseCurrency,
                          @RequestParam double amount,
                          Model model) {
        try {
            double result = currencyService.convertToKZT(baseCurrency, amount);
            model.addAttribute("baseCurrency", baseCurrency);
            model.addAttribute("amount", amount);
            model.addAttribute("result", result);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка конвертации: " + e.getMessage());
        }
        return "employees";
    }
}