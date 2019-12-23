package com.workouts.workoutsfrontend.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Workout {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("workoutName")
    private String workoutName;

    @JsonProperty("exercisesWithSeriesRepetitionsBreaks")
    private List<ExerciseWithParameters> exercisesWithSeriesRepetitionsBreaks;

    @JsonProperty("trainingDate")
    private LocalDate trainingDate;
}
