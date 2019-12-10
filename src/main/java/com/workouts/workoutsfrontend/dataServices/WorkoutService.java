package com.workouts.workoutsfrontend.temoraryData;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WorkoutService {

    private List<Workout> workoutList;
    private static WorkoutService workoutService;
    private ExerciseWithParametersService exerciseWithParametersService = ExerciseWithParametersService.getInstance();

    private WorkoutService() {
        this.workoutList = exampleData();
    }

    public static WorkoutService getInstance() {
        if (workoutService == null) {
            workoutService = new WorkoutService();
        }
        return workoutService;
    }

    public List<Workout> getWorkoutList() {
        return new ArrayList<>(workoutList);
    }

    private List<Workout> exampleData() {
        workoutList = new ArrayList<>();
        workoutList.add(new Workout("Example Workout", exerciseWithParametersService.getExerciseWithParametersList(), LocalDate.now().plus(2, ChronoUnit.DAYS)));
        workoutList.add(new Workout("Example workout nr 2", exerciseWithParametersService.getExerciseWithParametersList(), LocalDate.now().plus(5, ChronoUnit.DAYS)));
        return workoutList;
    }
}
