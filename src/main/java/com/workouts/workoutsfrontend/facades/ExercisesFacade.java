package com.workouts.workoutsfrontend.facades;

import com.workouts.workoutsfrontend.Dto.Exercise;
import com.workouts.workoutsfrontend.Dto.FavouriteExercise;
import com.workouts.workoutsfrontend.clients.ExercisesClient;

import java.util.List;

public class ExercisesFacade {

    private ExercisesClient exercisesClient = new ExercisesClient();

    public List<Exercise> getAllExercises() {
        return exercisesClient.getAllExercises();
    }

    public Exercise getExerciseByName(String name) {
        return exercisesClient.getExercise(name);
    }

    public List<FavouriteExercise> getUsersFavourites(String userMail) {
        return exercisesClient.getFavouriteExercises(userMail);
    }

    public void saveNewFavouriteExercise(String userMail, Long exerciseId) {
        exercisesClient.saveNewFavouriteExercise(userMail, exerciseId);
    }

    public void deleteFavouriteExercise(Long id) {
        exercisesClient.deleteFavouriteExercise(id);
    }
}
