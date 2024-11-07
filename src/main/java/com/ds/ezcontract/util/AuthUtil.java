package com.ds.ezcontract.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ds.ezcontract.models.User;
import com.ds.ezcontract.repositories.UserRepository;

@Component
public class AuthUtil {
    @Autowired
    UserRepository userRepository;

    public String loggedInEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
        return user.getEmail();
    }

    public User loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
        return user;
    }
}
