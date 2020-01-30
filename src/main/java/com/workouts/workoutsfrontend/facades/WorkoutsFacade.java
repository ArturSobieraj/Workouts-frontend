package com.workouts.workoutsfrontend.facades;

import com.workouts.workoutsfrontend.Dto.Workout;
import com.workouts.workoutsfrontend.clients.WorkoutsClient;

import java.time.LocalDate;
import java.util.List;

public class WorkoutsFacade {

    private WorkoutsClient workoutsClient = new WorkoutsClient();

    public List<Workout> getUsersWorkouts(String userMail) {
        return workoutsClient.getUsersWorkouts(userMail);
    }

    public void deleteWorkout(String workoutName) {
        workoutsClient.deleteWorkout(workoutName);
    }

    public Workout getWorkoutById(Long workoutId) {
        return workoutsClient.getWorkoutById(workoutId);
    }

    public void saveWorkout(String workoutName, LocalDate trainingDate, String userName) {
        workoutsClient.saveWorkout(workoutName, trainingDate, userName);
    }
}
