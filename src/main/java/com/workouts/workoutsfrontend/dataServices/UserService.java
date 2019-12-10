package com.workouts.workoutsfrontend.dataServices;

import com.workouts.workoutsfrontend.dataServices.Dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private List<User> user;
    private static UserService userService;

    private UserService() {
        this.user = exampleUser();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public List<User> getUser() {
        return new ArrayList<>(user);
    }

    public void addUser(User user) {
        this.user.add(user);
    }

    public List<User> getEnteringUser(String email) {
        return user.stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
    }

    private List<User> exampleUser() {
        List<User> user = new ArrayList<>();
        user.add(new User("example", "example"));
        return user;
    }
}
