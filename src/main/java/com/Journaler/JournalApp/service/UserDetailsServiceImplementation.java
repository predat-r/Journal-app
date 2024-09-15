package com.Journaler.JournalApp.service;

import com.Journaler.JournalApp.entity.UserEntity;
import com.Journaler.JournalApp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException(username);
    }


}
