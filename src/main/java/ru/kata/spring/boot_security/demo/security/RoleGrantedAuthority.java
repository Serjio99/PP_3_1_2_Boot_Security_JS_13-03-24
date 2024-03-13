package ru.kata.spring.boot_security.demo.security;


import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;


public class RoleGrantedAuthority implements GrantedAuthority {


    private final Role role;

    public RoleGrantedAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
