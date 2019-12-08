package com.workouts.workoutsfrontend.dashboardView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.workouts.workoutsfrontend.temoraryData.ExerciseWithParameters;
import com.workouts.workoutsfrontend.temoraryData.Workout;

import java.util.List;

public class WorkoutDetails extends VerticalLayout {

    private Grid<ExerciseWithParameters> exerciseWithParametersGrid = new Grid<>();

    public WorkoutDetails(){
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button editButton = new Button("Edit");
        Button closeButton = new Button("Close");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getExerciseName).setHeader("Exercise").setWidth("40%").setResizable(true);
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getNumberOfSeries).setHeader("Series");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getNumberOfRepetitions).setHeader("Repetitions");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getPauseTime).setHeader("Pause time");
        closeButton.addClickListener(event -> setDetailedWorkoutGridVisibility(null));
        buttonsLayout.add(editButton, closeButton);
        add(exerciseWithParametersGrid, buttonsLayout);
        //setSizeFull();
        //setVisible(true);
    }

    public void fillGrid(Workout workout){
        List<ExerciseWithParameters> exerciseWithParameters = workout.getExercisesWithSeriesRepetitionsBreaks();
        exerciseWithParametersGrid.setItems(exerciseWithParameters);
    }

    public void setDetailedWorkoutGridVisibility(Workout workout) {
        if (workout == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
