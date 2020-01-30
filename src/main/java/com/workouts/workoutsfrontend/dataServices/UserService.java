package com.workouts.workoutsfrontend.dataServices;

import com.workouts.workoutsfrontend.Dto.User;
import com.workouts.workoutsfrontend.facades.UsersFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private UsersFacade usersFacade = new UsersFacade();
    private static UserService userService;

    private UserService() {

    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public void addUser(String userName, String password) {
        usersFacade.saveUser(userName, password);
    }

    public User getEnteringUser(String email) {
        return usersFacade.getUser(email);
    }

    public List<User> getUsers() {
        return usersFacade.getUsers();
    }
}
