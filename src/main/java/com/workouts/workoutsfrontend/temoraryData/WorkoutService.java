package com.workouts.workoutsfrontend.temoraryData;


import java.time.LocalDate;
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
        workoutList.add(new Workout("Example Workout", exerciseWithParametersService.getExerciseWithParametersList(), LocalDate.now()));
        return workoutList;
    }
}
