package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {


    @GetMapping("/login")  // для обработки GET-запросов по адресу /login
    public String authentication() {

        return "login";
    }
}
