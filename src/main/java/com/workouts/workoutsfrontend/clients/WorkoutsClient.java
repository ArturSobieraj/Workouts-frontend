package com.workouts.workoutsfrontend.clients;

import com.workouts.workoutsfrontend.Dto.Workout;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

public class WorkoutsClient {

    private RestTemplate restTemplate = new RestTemplate();

    public List<Workout> getUsersWorkouts(String userMail) {
        URI getWorkoutsURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/workouts/get")
                .queryParam("userMail", userMail).build().encode().toUri();

        try {
            Workout[] workoutsResponse = restTemplate.getForObject(getWorkoutsURL, Workout[].class);
            return Arrays.asList(ofNullable(workoutsResponse).orElse(new Workout[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void deleteWorkout(String workoutName) {
        URI deleteWorkoutURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/workouts/delete")
                .queryParam("workoutName", workoutName).build().encode().toUri();
        restTemplate.delete(deleteWorkoutURL);
    }

    public Workout getWorkoutById(Long workoutId) {
        URI getByIdURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/workouts/getById")
                .queryParam("workoutId", workoutId).build().encode().toUri();

        try {
            Workout workoutResponse = restTemplate.getForObject(getByIdURL, Workout.class);
            return ofNullable(workoutResponse).orElse(new Workout());
        } catch (RestClientException e) {
            return new Workout();
        }
    }

    public void saveWorkout(String workoutName, LocalDate trainingDate, String userName) {
        URI saveWorkoutURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/workouts/add")
                .queryParam("userName", userName)
                .queryParam("workoutName", workoutName).build().encode().toUri();

        restTemplate.postForObject(saveWorkoutURL, trainingDate, Workout.class);
    }
}
