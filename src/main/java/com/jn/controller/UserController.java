package com.jn.controller;

import com.jn.entity.User;
import com.jn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public User doGetUser(@PathVariable Integer id) {
        return userService.findU(id);
    }

    @GetMapping("/users")
    public List<User> doGetAllUser() {
        return userService.findAll();
    }

    @PostMapping("/user")
    public User doAddUser(User user) {
        return userService.insertU(user);
    }


    @DeleteMapping("/user/{id}")
    public int doDelUser(@PathVariable Integer id) {
        return userService.deleteU(id);
    }

    @PutMapping("/user")
    public User doUpdateUser(User user) {
        return userService.updateU(user);
    }
}
