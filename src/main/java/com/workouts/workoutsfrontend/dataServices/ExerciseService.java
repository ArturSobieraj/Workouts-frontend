package com.workouts.workoutsfrontend.temoraryData;

import java.util.ArrayList;
import java.util.List;

public class ExerciseService {

    private List<Exercise> exerciseList;
    private static ExerciseService exerciseService;

    private ExerciseService() {
        this.exerciseList = exampleExercises();
    }

    public static ExerciseService getInstance() {
        if (exerciseService == null) {
            exerciseService = new ExerciseService();
        }
        return exerciseService;
    }

    public Exercise getExerciseByName(String name) {
        Exercise exerciseFound = new Exercise();
        for (Exercise candidate : exerciseList) {
            if (candidate.getExerciseName().equals(name)) {
                exerciseFound = candidate;
            }
        }
        return exerciseFound;
    }

    public List<Exercise> getExerciseList() {
        return new ArrayList<>(exerciseList);
    }

    private List<Exercise> exampleExercises() {
        exerciseList = new ArrayList<>();
        List<String> muscles = new ArrayList<>();
        muscles.add("Pectoralis major");
        List<String> secondaryMuscles = new ArrayList<>();
        secondaryMuscles.add("Anterior deltoid");
        List<String> equipment = new ArrayList<>();
        equipment.add("Barbell");
        exerciseList.add(new Exercise("Decline Bench Press Barbell",
                "description 1",
                "Chest",
                muscles,
                secondaryMuscles,
                equipment));
        exerciseList.add(new Exercise("Barbell Triceps Extension",
                "description 2",
                "Arms",
                muscles,
                secondaryMuscles,
                equipment));
        exerciseList.add(new Exercise("Dumbbell Goblet Squat",
                "description 3",
                "Legs",
                muscles,
                secondaryMuscles,
                equipment));
        return exerciseList;
    }
}
