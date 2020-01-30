package com.workouts.workoutsfrontend.dataServices;

import com.workouts.workoutsfrontend.Dto.Workout;
import com.workouts.workoutsfrontend.facades.WorkoutsFacade;

import java.time.LocalDate;
import java.util.List;

public class WorkoutService {

    private WorkoutsFacade workoutsFacade = new WorkoutsFacade();
    private static WorkoutService workoutService;
    private String userMail;
    private Long workoutId;

    private WorkoutService() {
    }

    public static WorkoutService getInstance() {
        if (workoutService == null) {
            workoutService = new WorkoutService();
        }
        return workoutService;
    }

    public Workout getWorkout(Long workoutId) {
        return workoutsFacade.getWorkoutById(workoutId);
    }

    public List<Workout> getWorkoutList(String userMail) {
        return workoutsFacade.getUsersWorkouts(userMail);
    }

    public void addWorkout(String workoutName, LocalDate trainingDate) {
        workoutsFacade.saveWorkout(workoutName, trainingDate, userMail);
    }

    public void updateWorkout(Workout workout) {

    }

    public void deleteWorkout(Workout workout) {
        workoutsFacade.deleteWorkout(workout.getWorkoutName());
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String mail) {
        this.userMail = mail;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }
}
