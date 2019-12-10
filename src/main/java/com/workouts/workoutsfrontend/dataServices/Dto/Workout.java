package com.workouts.workoutsfrontend.temoraryData;

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
public class Workout {
    private String workoutName;
    private List<ExerciseWithParameters> exercisesWithSeriesRepetitionsBreaks;
    private LocalDate trainingDate;
}
