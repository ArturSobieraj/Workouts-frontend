package com.workouts.workoutsfrontend.dataServices;

import com.workouts.workoutsfrontend.Dto.ExerciseWithParameters;
import com.workouts.workoutsfrontend.facades.ExercisesWithParametersFacade;

import java.util.ArrayList;
import java.util.List;

public class ExerciseWithParametersService {

    private static ExerciseWithParametersService exerciseWithParametersService;
    private ExerciseService exerciseService = ExerciseService.getInstance();
    private ExercisesWithParametersFacade exercisesWithParametersFacade = new ExercisesWithParametersFacade();

    private ExerciseWithParametersService() {

    }

    public static ExerciseWithParametersService getInstance() {
        if (exerciseWithParametersService == null) {
            exerciseWithParametersService = new ExerciseWithParametersService();
        }
        return exerciseWithParametersService;
    }

    public void addNewExercise(ExerciseWithParameters exerciseWithParameters) {
        exercisesWithParametersFacade.addNewExercise(exerciseWithParameters);
    }

    public void updateExercise(ExerciseWithParameters exerciseWithParameters) {
        exercisesWithParametersFacade.updateExercise(exerciseWithParameters);
    }

    public void deleteAddedExercise(Long exerciseId) {
        exercisesWithParametersFacade.deleteExercise(exerciseId);
    }

    public List<ExerciseWithParameters> getUsersExercises(String userName) {
        return exercisesWithParametersFacade.getUsersExercises(userName);
    }

    public List<ExerciseWithParameters> getWorkoutExercises(Long workoutId) {
        return exercisesWithParametersFacade.getExercisesByWorkout(workoutId);
    }
}
