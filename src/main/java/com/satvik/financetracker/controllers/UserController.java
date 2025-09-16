package com.satvik.financetracker.controllers;

import com.satvik.financetracker.models.User;
import com.satvik.financetracker.service.UserService;
import com.satvik.financetracker.service.implementation.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userSaved = userService.saveUser(user);
        logger.info("User saved: " + userSaved.toString());
        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    //handel exception
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId){
        User user = userService.getUser(userId);
        logger.info("User found: " + user.toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        logger.info("Users found: " + users.toString());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable UUID userId) {
        User user1 = userService.updateUser(user, userId);
        logger.info("User updated: " + user.toString());
        return ResponseEntity.ok(user1);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        logger.info("User deleted: " + userId);
        return ResponseEntity.ok("User successfully deleted");
    }


}
