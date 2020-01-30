package com.workouts.workoutsfrontend.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseWithParameters {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("exerciseDto")
    private Exercise exercises;

    @JsonProperty("numberOfSeries")
    private String numberOfSeries;

    @JsonProperty("numberOfRepetitions")
    private String numberOfRepetitions;

    @JsonProperty("pauseTime")
    private String pauseTime;

    @JsonProperty("workouts")
    private Workout workout;

    @JsonProperty("userName")
    private String userName;

    public ExerciseWithParameters(Exercise exercises, String numberOfSeries, String numberOfRepetitions, String pauseTime, Workout workout) {
        this.exercises = exercises;
        this.numberOfSeries = numberOfSeries;
        this.numberOfRepetitions = numberOfRepetitions;
        this.pauseTime = pauseTime;
        this.workout = workout;
    }

    public String getExerciseName() {
        return this.exercises.getExerciseName();
    }
}
