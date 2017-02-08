package com.model;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    private User user;


    public UserService(User user) {
        this.user = user;
    }

    public UserService() {

    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
}