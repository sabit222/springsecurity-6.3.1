package com.propro.controller;

import com.propro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView showRegistrationForm() {
        // Возвращаем страницу регистрации
        return new ModelAndView("register"); // Имя HTML-шаблона для страницы регистрации
    }

    @PostMapping
    public ModelAndView registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        try {
            userService.registerUser(username, password);
            // При успешной регистрации перенаправляем на страницу успеха или входа
            return new ModelAndView("redirect:/login"); // Измените на нужный маршрут
        } catch (RuntimeException e) {
            // При ошибке регистрации возвращаем на страницу регистрации с сообщением об ошибке
            ModelAndView mav = new ModelAndView("register");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }
}