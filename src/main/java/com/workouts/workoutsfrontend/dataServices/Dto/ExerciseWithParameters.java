package com.workouts.workoutsfrontend.dataServices.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ExerciseWithParameters {
    private Exercise exercise;
    private String numberOfSeries;
    private String numberOfRepetitions;
    private String pauseTime;

    public String getExerciseName() {
        return this.exercise.getExerciseName();
    }
}
