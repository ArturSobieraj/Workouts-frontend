package com.workouts.workoutsfrontend.dataServices;

import com.workouts.workoutsfrontend.Dto.Exercise;
import com.workouts.workoutsfrontend.Dto.FavouriteExercise;
import com.workouts.workoutsfrontend.facades.ExercisesFacade;

import java.util.ArrayList;
import java.util.List;

public class ExerciseService {

    private static ExerciseService exerciseService;
    private ExercisesFacade exercisesFacade = new ExercisesFacade();

    private ExerciseService() {

    }

    public static ExerciseService getInstance() {
        if (exerciseService == null) {
            exerciseService = new ExerciseService();
        }
        return exerciseService;
    }

    public Exercise getExerciseByName(String name) {
        return exercisesFacade.getExerciseByName(name);
    }

    public List<FavouriteExercise> getFavouriteExercisesList(String userMail) {
        return exercisesFacade.getUsersFavourites(userMail);
    }

    public void addNewFavouriteExercise(String userMail, Long exerciseId) {
        exercisesFacade.saveNewFavouriteExercise(userMail, exerciseId);
    }

    public void deleteFromFavourites(Long id) {
        exercisesFacade.deleteFavouriteExercise(id);
    }

    public List<Exercise> getExerciseList() {
        return exercisesFacade.getAllExercises();
    }
}
