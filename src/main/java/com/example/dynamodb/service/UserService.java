package com.example.dynamodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dynamodb.model.User;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private DynamoDbTable<User> getUserTable() {
        return dynamoDbEnhancedClient.table("Users", TableSchema.fromBean(User.class));
    }

    // Create user
    public void addUser(User user) {
        try {
            DynamoDbTable<User> userTable = getUserTable();
            userTable.putItem(user);  // Insert user into DynamoDB
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }

    // Get user by ID
    public Optional<User> getUserById(String userId) {
        try {
            DynamoDbTable<User> userTable = getUserTable();
            User user = userTable.getItem(r -> r.key(k -> k.partitionValue(userId)));
            return Optional.ofNullable(user);
        } catch (DynamoDbException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Update user
    public void updateUser(User user) {
        try {
            DynamoDbTable<User> userTable = getUserTable();
            userTable.updateItem(user);  // Update existing user in DynamoDB
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }

    // Delete user by ID
    public void deleteUser(String userId) {
        try {
            DynamoDbTable<User> userTable = getUserTable();
            userTable.deleteItem(r -> r.key(k -> k.partitionValue(userId)));
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }

    // Validate user data
    public boolean validateUser(User user) {
        return user.getName() != null && user.getEmail() != null;
    }
    
    public List<User> getAllUsers() {
        try {
            DynamoDbTable<User> userTable = getUserTable();
            Iterator<User> results = userTable.scan(ScanEnhancedRequest.builder().build()).items().iterator();
            List<User> users = new ArrayList<>();
            results.forEachRemaining(users::add);
            return users;
        } catch (DynamoDbException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Update user by ID
    public void updateUserById(String userId, User updatedUser) {
        try {
            DynamoDbTable<User> userTable = getUserTable();
            User existingUser = userTable.getItem(r -> r.key(k -> k.partitionValue(userId)));
            if (existingUser != null) {
                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());
                userTable.updateItem(existingUser);  // Update existing user
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }
}
