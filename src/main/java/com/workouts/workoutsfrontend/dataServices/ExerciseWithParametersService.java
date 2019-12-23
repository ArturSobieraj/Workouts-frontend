package com.workouts.workoutsfrontend.dataServices;

import com.workouts.workoutsfrontend.Dto.ExerciseWithParameters;

import java.util.ArrayList;
import java.util.List;

public class ExerciseWithParametersService {

    private List<ExerciseWithParameters> exerciseWithParametersList;
    private List<ExerciseWithParameters> exercisesForNewWorkout;
    private static ExerciseWithParametersService exerciseWithParametersService;
    private ExerciseService exerciseService = ExerciseService.getInstance();

    private ExerciseWithParametersService() {
        this.exerciseWithParametersList = exampleData();
        this.exercisesForNewWorkout = new ArrayList<>();
    }

    public static ExerciseWithParametersService getInstance() {
        if (exerciseWithParametersService == null) {
            exerciseWithParametersService = new ExerciseWithParametersService();
        }
        return exerciseWithParametersService;
    }

    public void addNewExercise(ExerciseWithParameters exerciseWithParameters) {
        this.exercisesForNewWorkout.add(exerciseWithParameters);
    }

    public void deleteAddedExercise(ExerciseWithParameters exerciseWithParameters) {
        this.exercisesForNewWorkout.remove(exerciseWithParameters);
    }

    public List<ExerciseWithParameters> getExercisesForNewWorkout() {
        return new ArrayList<>(exercisesForNewWorkout);
    }

    public List<ExerciseWithParameters> getExerciseWithParametersList() {
        return new ArrayList<>(exerciseWithParametersList);
    }

    public void setClearList() {
        this.exercisesForNewWorkout = new ArrayList<>();
    }

    public void setExercisesForNewWorkout(List<ExerciseWithParameters> list) {
        this.exercisesForNewWorkout = list;
    }

    private List<ExerciseWithParameters> exampleData() {
        exerciseWithParametersList = new ArrayList<>();
        exerciseWithParametersList.add(new ExerciseWithParameters(exerciseService.getExerciseList().get(0), "4", "10", "60"));
        exerciseWithParametersList.add(new ExerciseWithParameters(exerciseService.getExerciseList().get(1), "4", "10", "60"));
        exerciseWithParametersList.add(new ExerciseWithParameters(exerciseService.getExerciseList().get(2), "4", "10", "60"));

        return exerciseWithParametersList;
    }
}
