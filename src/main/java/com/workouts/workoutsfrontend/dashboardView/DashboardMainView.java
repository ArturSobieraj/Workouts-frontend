package com.workouts.workoutsfrontend.dashboardView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.temoraryData.*;


@Route("dashboard")
public class DashboardMainView extends VerticalLayout {


    public DashboardMainView() {

        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout buttonsLayout = new VerticalLayout();
        HorizontalLayout gridsLayout = new HorizontalLayout();

        Button workoutsList = new Button("List of workouts");
        Button favourites = new Button("Favourites");
        Button trainingList = new Button("List of trainings");
        buttonsLayout.add(workoutsList);
        buttonsLayout.add(favourites);
        buttonsLayout.add(trainingList);

        Label calendarLabel = new Label("Next training date");
        horizontalLayout.add(buttonsLayout, calendarLabel);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        WorkoutService workoutService = WorkoutService.getInstance();

        Grid<Workout> workoutGrid = new Grid<>();
        workoutGrid.setItems(workoutService.getWorkoutList());
        workoutGrid.addColumn(Workout::getWorkoutName).setHeader("Name");
        workoutGrid.addColumn(workout -> workout.getExercisesWithSeriesRepetitionsBreaks().size()).setHeader("Exercises");
        workoutGrid.addColumn(Workout::getLocalDate).setHeader("Date");
        mainLayout.add(horizontalLayout, workoutGrid);
        add(mainLayout);
        setSizeFull();
    }
}
