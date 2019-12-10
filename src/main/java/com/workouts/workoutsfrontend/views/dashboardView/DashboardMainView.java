package com.workouts.workoutsfrontend.dashboardView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.temoraryData.*;

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
        Button listOfExercises = new Button("List of exercises");
        listOfExercises.addClickListener(event -> listOfExercisesAction(listOfExercises));
        buttonsLayout.add(favourites);
        buttonsLayout.add(listOfExercises);

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

    public LocalDate getDateOfNextTraining(List<Workout> workoutList) {
       List<LocalDate> dateList = workoutList.stream()
               .map(Workout::getTrainingDate)
               .sorted()
               .collect(Collectors.toList());
        return dateList.get(0);
    }

    public void listOfExercisesAction(Button listOfExercisesButton) {
        listOfExercisesButton.getUI().ifPresent(ui -> ui.navigate("exercisesList"));
    }
}
