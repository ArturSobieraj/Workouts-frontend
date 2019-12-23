package com.workouts.workoutsfrontend.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FavouriteExercise {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user")
    private User user;

    @JsonProperty("userFavouriteExercise")
    private Exercise exercise;

    public FavouriteExercise(User user, Exercise exercise) {
        this.user = user;
        this.exercise = exercise;
    }
}
