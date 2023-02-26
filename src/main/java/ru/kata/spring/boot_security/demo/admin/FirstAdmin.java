package ru.kata.spring.boot_security.demo.admin;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FirstAdmin {
    private final UserService userService;
    private final RoleService roleService;

    public FirstAdmin(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createFirstAdmin() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleService.saveRole(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleService.saveRole(roleUser);
        userService.saveUser(new User("user@mail.ru", "user",
                "User", "User", 20, List.of(roleUser)));
        userService.saveUser(new User("admin@mail.ru", "admin",
                "Admin", "Admin", 20, List.of(roleAdmin)));
        userService.saveUser(new User("user1@mail.ru", "user1",
                "User", "User", 20, List.of(roleUser)));
        userService.saveUser(new User("admin1@mail.ru", "admin1",
                "Admin", "Admin", 20, List.of(roleAdmin)));
    }
}
