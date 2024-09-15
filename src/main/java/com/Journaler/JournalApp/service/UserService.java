package com.Journaler.JournalApp.service;

import com.Journaler.JournalApp.entity.UserEntity;
import com.Journaler.JournalApp.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public boolean saveUser(UserEntity user) {
        userRepo.save(user);
        return true;
    }

    public boolean saveNewUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
        return true;
    }

    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    public UserEntity getUserById(ObjectId id) {
        return userRepo.findById(id).orElse(null);
    }

    public boolean deleteUser(ObjectId id) {
        userRepo.deleteById(id);
        return true;
    }


    public UserEntity findByusername(String username) {
        return userRepo.findByUsername(username);
    }

    public void deletebyUsername(String name) {
        userRepo.deleteByUsername(name);
    }
}
