package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.NoSuchUserException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RESTController {
    private final UserService userService;

    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") long id) {
        if (userService.getUser(id) == null) {
            throw new NoSuchUserException("User not found!");
        }
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user, user.getId());
        return userService.getUser(user.getId());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
