package com.workouts.workoutsfrontend.dataServices;

import com.workouts.workoutsfrontend.Dto.Workout;
import com.workouts.workoutsfrontend.facades.WorkoutsFacade;
import java.util.ArrayList;
import java.util.List;

public class WorkoutService {

    private WorkoutsFacade workoutsFacade = new WorkoutsFacade();
    private List<Workout> workoutList;
    private static WorkoutService workoutService;
    private String userMail;

    private WorkoutService() {
        workoutList = new ArrayList<>();
    }

    public static WorkoutService getInstance() {
        if (workoutService == null) {
            workoutService = new WorkoutService();
        }
        return workoutService;
    }

    public List<Workout> getWorkoutList(String userMail) {
        if (workoutList.size() == 0) {
            getWorkouts(userMail);
            return new ArrayList<>(workoutList);
        } else
            return new ArrayList<>(workoutList);
    }

    public void addWorkout(Workout workout) {
        workoutList.add(workout);
    }

    public void deleteWorkout(Workout workout) {
        workoutsFacade.deleteWorkout(workout.getWorkoutName());
    }

    private void getWorkouts(String userMail) {
        workoutList = workoutsFacade.getUsersWorkouts(userMail);
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String mail) {
        userMail = mail;
    }
}
