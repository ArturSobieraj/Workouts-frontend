package com.workouts.workoutsfrontend.facades;

import com.workouts.workoutsfrontend.Dto.User;
import com.workouts.workoutsfrontend.clients.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class UsersFacade {

    private UserClient userClient = new UserClient();

    public List<User> getUsers() {
        return userClient.getUsers();
    }

    public User getUser(String userMail) {
        return userClient.getUser(userMail);
    }

    public void saveUser(User user) {
        userClient.saveUser(user);
    }
}
