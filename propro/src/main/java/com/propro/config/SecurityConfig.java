package com.propro.config;


import com.propro.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Включение CSRF защиты
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                // Управление доступом
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/register").permitAll() // Доступ к странице регистрации открыт для всех
                                .requestMatchers("/login").permitAll() // Доступ к странице входа открыт для всех
                                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                // Настройка формы входа
                .formLogin(formLogin ->
                        formLogin
                                .defaultSuccessUrl("/employee", true) // Перенаправление после успешного входа
                                .permitAll()
                )
                // Настройка выхода из системы
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // URL для выхода из системы
                                .logoutSuccessUrl("/login?logout") // URL для перенаправления после выхода
                                .permitAll()
                );


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}