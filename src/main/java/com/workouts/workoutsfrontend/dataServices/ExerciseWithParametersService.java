package com.workouts.workoutsfrontend.temoraryData;

import java.util.ArrayList;
import java.util.List;

public class ExerciseWithParametersService {

    private List<ExerciseWithParameters> exerciseWithParametersList;
    private static ExerciseWithParametersService exerciseWithParametersService;
    private ExerciseService exerciseService = ExerciseService.getInstance();

    private ExerciseWithParametersService() {
        this.exerciseWithParametersList = exampleData();
    }

    public static ExerciseWithParametersService getInstance() {
        if (exerciseWithParametersService == null) {
            exerciseWithParametersService = new ExerciseWithParametersService();
        }
        return exerciseWithParametersService;
    }

    public List<ExerciseWithParameters> getExerciseWithParametersList() {
        return new ArrayList<>(exerciseWithParametersList);
    }

    private List<ExerciseWithParameters> exampleData() {
        exerciseWithParametersList = new ArrayList<>();
        exerciseWithParametersList.add(new ExerciseWithParameters(exerciseService.getExerciseList().get(0), "4", "10", "60"));
        exerciseWithParametersList.add(new ExerciseWithParameters(exerciseService.getExerciseList().get(1), "4", "10", "60"));
        exerciseWithParametersList.add(new ExerciseWithParameters(exerciseService.getExerciseList().get(2), "4", "10", "60"));

        return exerciseWithParametersList;
    }
}
