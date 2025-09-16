package com.satvik.financetracker.service;

import com.satvik.financetracker.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

User saveUser(User user);
User getUser(UUID id);
User updateUser(User user, UUID uuid);
void deleteUser(UUID uuid);
List<User> getUsers();
void login(String username, String password);

}
