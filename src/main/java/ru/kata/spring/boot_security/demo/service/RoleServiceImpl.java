package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class RoleServiceImpl implements ru.kata.spring.boot_security.demo.service.RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> getRoles() {
        List<Role> list = roleDao.findAll();
        return new HashSet<>(list);
    }
}
