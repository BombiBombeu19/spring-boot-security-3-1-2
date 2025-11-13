package ru.kata.spring_boot_security_3_1_2.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.kata.spring_boot_security_3_1_2.SpringBootSecurity312Application;

public class UserInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootSecurity312Application.class);
    }
}