package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;
import ru.kata.spring.boot_security.demo.util.UserResponseException;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public RestApiController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public List<User> getAllUSer() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.findOneId(id);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getAllRoles() {
        Set<Role> roleList = roleService.getRoles();
        return ResponseEntity.ok(roleList);
    }


    @PostMapping("/user")
    public ResponseEntity<HttpStatus> createNewUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errorMessage.append(fieldErrors).append("::: \n"));
            throw new UserNotCreatedException(errorMessage.toString());
        }
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(UserNotFoundException e) {
        UserResponseException userResponseException = new UserResponseException("User Not Found");
        return new ResponseEntity<>(userResponseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(UserNotCreatedException e) {
        UserResponseException userResponseException = new UserResponseException(e.getMessage());
        return new ResponseEntity<>(userResponseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(SQLIntegrityConstraintViolationException e) {
        UserResponseException userResponseException = new UserResponseException("User with this email already exists");
        return new ResponseEntity<>(userResponseException, HttpStatus.BAD_REQUEST);
    }
}