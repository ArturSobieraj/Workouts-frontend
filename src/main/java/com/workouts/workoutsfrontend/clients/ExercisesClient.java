package com.workouts.workoutsfrontend.clients;

import com.workouts.workoutsfrontend.Dto.Exercise;
import com.workouts.workoutsfrontend.Dto.ExerciseWithParameters;
import com.workouts.workoutsfrontend.Dto.FavouriteExercise;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

public class ExercisesClient {

    private RestTemplate restTemplate = new RestTemplate();

    public List<Exercise> getAllExercises() {
        URI getAllExercisesURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/exercises/all").build().encode().toUri();

        try {
            Exercise[] exercisesResponse = restTemplate.getForObject(getAllExercisesURL, Exercise[].class);
            return Arrays.asList(ofNullable(exercisesResponse).orElse(new Exercise[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Exercise getExercise(String name) {
        URI getExerciseByNameURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/exercises/byName")
                .queryParam("name", name).build().encode().toUri();

        try {
            Exercise exerciseResponse = restTemplate.getForObject(getExerciseByNameURL, Exercise.class);
            return ofNullable(exerciseResponse).orElse(new Exercise());
        } catch (RestClientException e) {
            return new Exercise();
        }
    }

    public List<FavouriteExercise> getFavouriteExercises(String userMail) {
        URI getUserFavouriteExercisesURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/favExercises/get")
                .queryParam("userMail", userMail).build().encode().toUri();

        try {
            FavouriteExercise[] favouriteExercisesResponse = restTemplate.getForObject(getUserFavouriteExercisesURL, FavouriteExercise[].class);
            return Arrays.asList(ofNullable(favouriteExercisesResponse).orElse(new FavouriteExercise[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void saveNewFavouriteExercise(String userMail, Long exerciseId) {
        URI saveFavouriteExerciseURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/favExercises/add")
                .queryParam("userMail", userMail)
                .queryParam("exerciseId", exerciseId).build().encode().toUri();

        restTemplate.postForObject(saveFavouriteExerciseURL, null, FavouriteExercise.class);
    }

    public void deleteFavouriteExercise(Long id) {
        URI deleteFavouriteExerciseURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/favExercises/delete")
                .queryParam("id", id).build().encode().toUri();

        restTemplate.delete(deleteFavouriteExerciseURL);
    }

    public List<ExerciseWithParameters> getUsersExercises(String userName) {
        URI getAllForNewWorkout = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/exwithparams/getallbyuser")
                .queryParam("userName", userName).build().encode().toUri();

        try {
            ExerciseWithParameters[] exerciseWithParametersResponse = restTemplate.getForObject(getAllForNewWorkout, ExerciseWithParameters[].class);
            return Arrays.asList(ofNullable(exerciseWithParametersResponse).orElse(new ExerciseWithParameters[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<ExerciseWithParameters> getExercisesByWorkout(Long workoutId) { //utworzyć metodę w backendzie
        URI getAllByWorkout = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/exwithparams/getallbyworkout")
                .queryParam("workoutId", workoutId).build().encode().toUri();

        try {
            ExerciseWithParameters[] exerciseWithParametersResponse = restTemplate.getForObject(getAllByWorkout, ExerciseWithParameters[].class);
            return Arrays.asList(ofNullable(exerciseWithParametersResponse).orElse(new ExerciseWithParameters[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void updateExercise(ExerciseWithParameters exerciseWithParameters) {
        URI updateExerciseURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/exwithparams/edit") .build().encode().toUri();

        restTemplate.put(updateExerciseURL, exerciseWithParameters);
    }

    public void addNewExercise(ExerciseWithParameters exerciseWithParameters) {
        URI addNewExerciseURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/exwithparams/add").build().encode().toUri();

        restTemplate.postForObject(addNewExerciseURL, exerciseWithParameters, ExerciseWithParameters.class);
    }

    public void deleteExercise(Long exerciseId) {
        URI deleteExerciseURL = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/exwithparams/delete")
                .queryParam("exerciseId", exerciseId).build().encode().toUri();

        restTemplate.delete(deleteExerciseURL);
    }
}
