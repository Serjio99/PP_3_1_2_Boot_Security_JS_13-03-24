package ru.kata.spring.boot_security.demo.util;

public class UserResponseException {

    private String info;

    public UserResponseException(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}