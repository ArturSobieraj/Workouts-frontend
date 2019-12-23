package com.workouts.workoutsfrontend.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseWithParameters {


    @JsonProperty("exerciseDto")
    private Exercise exercise;

    @JsonProperty("numberOfSeries")
    private String numberOfSeries;

    @JsonProperty("numberOfRepetitions")
    private String numberOfRepetitions;

    @JsonProperty("pauseTime")
    private String pauseTime;

    public String getExerciseName() {
        return this.exercise.getExerciseName();
    }
}
