package com.Journaler.JournalApp.Controller;
import com.Journaler.JournalApp.entity.UserEntity;
import com.Journaler.JournalApp.service.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserService userService;


    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping
    public boolean updateUser(@RequestBody UserEntity user){
        
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       String username = auth.getName();
       UserEntity old  = userService.findByusername(username);
       old.setUsername(user.getUsername());
       old.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
       return userService.saveUser(old);


    }
    @DeleteMapping
    public boolean deleteUser(@RequestBody UserEntity user){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.deletebyUsername(auth.getName());
        return true;
    }

}
