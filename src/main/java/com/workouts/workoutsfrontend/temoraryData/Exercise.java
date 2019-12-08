package com.workouts.workoutsfrontend.temoraryData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Exercise {
    private String exerciseName;
    private String description;
    private String category;
    private List<String> muscles;
    private List<String> secondaryMuscles;
    private List<String> equipment;
}
