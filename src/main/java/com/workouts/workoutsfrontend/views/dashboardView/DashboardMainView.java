package com.workouts.workoutsfrontend.views.dashboardView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.dataServices.*;
import com.workouts.workoutsfrontend.Dto.Workout;
import com.workouts.workoutsfrontend.views.WorkoutView.NewWorkoutView;
import com.workouts.workoutsfrontend.views.exerciseViews.FavouriteExercises;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Route("dashboard")
public class DashboardMainView extends VerticalLayout implements HasUrlParameter<String> {

    private WorkoutService workoutService = WorkoutService.getInstance();
    private WorkoutDetails workoutDetails = new WorkoutDetails();
    private Grid<Workout> workoutGrid = new Grid<>();

    public DashboardMainView() {

        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout buttonsLayout = new VerticalLayout();
        VerticalLayout userButtonsLayout = new VerticalLayout();
        HorizontalLayout gridsLayout = new HorizontalLayout();

        Button favourites = new Button("Ulubione ćwiczenia");
        favourites.addClickListener(event -> goToFavouriteExercises());
        Button listOfExercises = new Button("Lista ćwiczeń");
        listOfExercises.addClickListener(event -> listOfExercisesAction(listOfExercises));
        Button edit = new Button("Edytuj trening");
        edit.addClickListener(event -> editWorkout());
        Button deleteWorkout = new Button("Usuń trening");
        deleteWorkout.addClickListener(event -> deleteWorkout());
        buttonsLayout.add(favourites, listOfExercises, edit, deleteWorkout);

        Button logout = new Button("Wyloguj");
        Button changePassword = new Button("Zmień hasło");
        Button deleteAccount = new Button("Usuń konto");
        userButtonsLayout.add(logout, changePassword, deleteAccount);
        userButtonsLayout.setAlignItems(Alignment.START);

        Label calendarLabel = new Label("Następny trening: " + getDateOfNextTraining(workoutService.getWorkoutList(workoutService.getUserMail())));
        calendarLabel.setSizeFull();
        horizontalLayout.add(buttonsLayout, userButtonsLayout, calendarLabel);

        workoutDetails.setDetailedWorkoutGridVisibility(null);
        workoutGrid.setItems(workoutService.getWorkoutList(workoutService.getUserMail()));
        workoutGrid.addColumn(Workout::getWorkoutName).setHeader("Nazwa");
        workoutGrid.addColumn(workout -> workout.getExercisesWithSeriesRepetitionsBreaks().size()).setHeader("Ilość ćwiczeń");
        workoutGrid.addColumn(Workout::getTrainingDate).setHeader("Data");

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
        workoutGrid.setItems(workoutService.getWorkoutList(workoutService.getUserMail()));
    }

    private String getDateOfNextTraining(List<Workout> workoutList) {
        if (workoutList.size() != 0) {
            List<LocalDate> dateList = workoutList.stream()
                    .map(Workout::getTrainingDate)
                    .sorted()
                    .collect(Collectors.toList());
            return dateList.get(0).toString();
        } else {
            return "No workouts";
        }
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
            Notification.show("Nic nie zaznaczono", 3000, Notification.Position.MIDDLE);
        }
    }

    private void deleteWorkout() {
        if (workoutGrid.asSingleSelect().getValue() != null) {
            workoutService.deleteWorkout(workoutGrid.asSingleSelect().getValue());
            refresh();
        } else {
            Notification.show("Nic nie zaznaczono", 3000, Notification.Position.MIDDLE);
        }
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (!parameter.equals("zero parameter")) {
            workoutService.setUserMail(parameter);
            refresh();
        }
    }
}
