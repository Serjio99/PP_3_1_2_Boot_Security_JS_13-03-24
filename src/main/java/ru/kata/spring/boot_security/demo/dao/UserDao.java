package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {


    @Query("select u from User u left join fetch u.role where u.username = (:username)")
    Optional<User> findByName(@Param("username") String username);

}
