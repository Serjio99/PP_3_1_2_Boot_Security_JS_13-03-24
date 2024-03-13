package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {  // сделал кконтроллер для запросов с главной страницы

    @GetMapping("/")
    public String welcomePage() {

        return "welcome";
    }
}
