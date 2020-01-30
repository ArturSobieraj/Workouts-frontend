package com.workouts.workoutsfrontend.facades;

import com.workouts.workoutsfrontend.Dto.ExerciseWithParameters;
import com.workouts.workoutsfrontend.clients.ExercisesClient;

import java.util.List;

public class ExercisesWithParametersFacade {

    public ExercisesClient exercisesClient = new ExercisesClient();

    public List<ExerciseWithParameters> getUsersExercises(String userName) {
        return exercisesClient.getUsersExercises(userName);
    }

    public List<ExerciseWithParameters> getExercisesByWorkout(Long workoutId) {
        return exercisesClient.getExercisesByWorkout(workoutId);
    }

    public void updateExercise(ExerciseWithParameters exerciseWithParameters) {
        exercisesClient.updateExercise(exerciseWithParameters);
    }

    public void addNewExercise(ExerciseWithParameters exerciseWithParameters) {
        exercisesClient.addNewExercise(exerciseWithParameters);
    }

    public void deleteExercise(Long exerciseId) {
        exercisesClient.deleteExercise(exerciseId);
    }
}
