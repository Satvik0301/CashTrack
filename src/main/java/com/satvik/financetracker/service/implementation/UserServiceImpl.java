package com.satvik.financetracker.service.implementation;

import com.satvik.financetracker.models.User;
import com.satvik.financetracker.repositories.UserRepo;
import com.satvik.financetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        User save = userRepo.save(user);
        logger.info("User saved: " + user.getUsername());
        return save;
    }

    @Override
    public User getUser(UUID id) {
        Optional<User> optionalUser = userRepo.findById(id);
        User user = optionalUser.orElseThrow(()-> new RuntimeException("User not found"));
        logger.info("User found: " + user.getUsername());
        return user;
    }

    @Override
    public User updateUser(User user, UUID uuid) {
        User user1 = userRepo.findById(uuid).orElseThrow(()-> new RuntimeException("User not found"));
        user1.setAll(user);
        User user2 = userRepo.save(user1);
        logger.info("User updated");
        return user2;
    }

    @Override
    public void deleteUser(UUID uuid) {
        userRepo.findById(uuid).orElseThrow(()-> new RuntimeException("User not found"));
        userRepo.deleteById(uuid);
        logger.info("User deleted: " + uuid);

    }

    @Override
    public List<User> getUsers() {

        List<User> users = userRepo.findAll();
        return users;
    }

    @Override
    public void login(String username, String password) {

    }
}
