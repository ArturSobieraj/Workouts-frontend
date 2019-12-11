package com.workouts.workoutsfrontend.views.dashboardView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.dataServices.*;
import com.workouts.workoutsfrontend.dataServices.Dto.Workout;
import com.workouts.workoutsfrontend.views.WorkoutView.NewWorkoutView;
import com.workouts.workoutsfrontend.views.exerciseViews.FavouriteExercises;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Route("dashboard")
public class DashboardMainView extends VerticalLayout {

    private WorkoutDetails workoutDetails = new WorkoutDetails();
    private Grid<Workout> workoutGrid = new Grid<>();
    private WorkoutService workoutService = WorkoutService.getInstance();

    public DashboardMainView() {

        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout buttonsLayout = new VerticalLayout();
        HorizontalLayout gridsLayout = new HorizontalLayout();

        Button favourites = new Button("Favourite exercises");
        favourites.addClickListener(event -> goToFavouriteExercises());
        Button listOfExercises = new Button("List of exercises");
        listOfExercises.addClickListener(event -> listOfExercisesAction(listOfExercises));
        Button edit = new Button("Edit workout");
        edit.addClickListener(event -> editWorkout());
        Button deleteWorkout = new Button("Delete Workout");
        deleteWorkout.addClickListener(event -> deleteWorkout());
        buttonsLayout.add(favourites, listOfExercises, edit, deleteWorkout);


        Label calendarLabel = new Label("Next training date: " + getDateOfNextTraining(workoutService.getWorkoutList()));
        horizontalLayout.add(buttonsLayout, calendarLabel);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        workoutDetails.setDetailedWorkoutGridVisibility(null);
        workoutGrid.setItems(workoutService.getWorkoutList());
        workoutGrid.addColumn(Workout::getWorkoutName).setHeader("Name");
        workoutGrid.addColumn(workout -> workout.getExercisesWithSeriesRepetitionsBreaks().size()).setHeader("Exercises");
        workoutGrid.addColumn(Workout::getTrainingDate).setHeader("Date");

        gridsLayout.setSizeFull();
        workoutGrid.setSizeUndefined();
        mainLayout.add(horizontalLayout, gridsLayout);
        add(mainLayout);
        gridsLayout.add(workoutGrid, workoutDetails);
        setSizeFull();
        refresh();

        workoutGrid.asSingleSelect().addValueChangeListener(event -> {
            workoutDetails.setDetailedWorkoutGridVisibility(workoutGrid.asSingleSelect().getValue());
            if (workoutDetails.isVisible()) {
                workoutDetails.fillGrid(workoutGrid.asSingleSelect().getValue());
            }
        });
    }

    private void refresh() {
        workoutGrid.setItems(workoutService.getWorkoutList());
    }

    private LocalDate getDateOfNextTraining(List<Workout> workoutList) {
       List<LocalDate> dateList = workoutList.stream()
               .map(Workout::getTrainingDate)
               .sorted()
               .collect(Collectors.toList());
        return dateList.get(0);
    }

    private void listOfExercisesAction(Button listOfExercisesButton) {
        listOfExercisesButton.getUI().ifPresent(ui -> ui.navigate("exercisesList"));
    }

    private void goToFavouriteExercises() {
        getUI().ifPresent(ui -> ui.navigate(FavouriteExercises.class, "zeroParameter"));
    }

    private void editWorkout() {
        if (workoutGrid.asSingleSelect().getValue() != null) {
            getUI().ifPresent(ui -> ui.navigate(NewWorkoutView.class, workoutGrid.asSingleSelect().getValue().getWorkoutName()));
        } else {
            Notification.show("Nothing selected", 3000, Notification.Position.MIDDLE);
        }
    }

    private void deleteWorkout() {
        if (workoutGrid.asSingleSelect().getValue() != null) {
            workoutService.deleteWorkout(workoutGrid.asSingleSelect().getValue());
            refresh();
        } else {
            Notification.show("Nothing selected", 3000, Notification.Position.MIDDLE);
        }
    }
}
