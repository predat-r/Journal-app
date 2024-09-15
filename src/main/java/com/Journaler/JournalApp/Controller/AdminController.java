package com.Journaler.JournalApp.Controller;

import com.Journaler.JournalApp.entity.UserEntity;
import com.Journaler.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
