package com.workouts.workoutsfrontend.clients;

import com.vaadin.flow.component.notification.Notification;
import com.workouts.workoutsfrontend.Dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

public class UserClient {

    private RestTemplate restTemplate = new RestTemplate();

    public List<User> getUsers() {
        URI getUsersURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/users/get").build().encode().toUri();

        try {
            User[] usersResponse = restTemplate.getForObject(getUsersURL, User[].class);
            return Arrays.asList(ofNullable(usersResponse).orElse(new User[0]));
        } catch (RestClientException e) {
            Notification.show("Error", 2000, Notification.Position.MIDDLE);
            return new ArrayList<>();
        }
    }

    public User getUser(String userMail) {
        URI getUserURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/users/getOne")
                .queryParam("userMail", userMail).build().encode().toUri();

        try {
            return restTemplate.getForObject(getUserURL, User.class);
        } catch (RestClientException e) {
            Notification.show("Error", 2000, Notification.Position.MIDDLE);
            return new User();
        }
    }

    public void saveUser(String userName, String password) {
        URI saveUserURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/users/add")
                .queryParam("userName", userName)
                .queryParam("password", password).build().encode().toUri();

        restTemplate.postForObject(saveUserURL, null, User.class);
    }
}
