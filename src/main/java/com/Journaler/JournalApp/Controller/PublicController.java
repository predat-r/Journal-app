package com.Journaler.JournalApp.Controller;
import com.Journaler.JournalApp.entity.UserEntity;
import com.Journaler.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public boolean createuser(@RequestBody UserEntity user)
    {
        userService.saveNewUser(user);
        return true;
    }

}
