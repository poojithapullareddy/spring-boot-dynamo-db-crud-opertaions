package com.example.dynamodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dynamodb.model.User;
import com.example.dynamodb.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (!userService.validateUser(user)) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }
        userService.validateUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Update user by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable("id") String userId, @RequestBody User user) {
        userService.updateUserById(userId, user);
        return ResponseEntity.ok("User updated successfully");
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
