package com.workouts.workoutsfrontend.views.dashboardView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.workouts.workoutsfrontend.Dto.ExerciseWithParameters;
import com.workouts.workoutsfrontend.Dto.Workout;
import com.workouts.workoutsfrontend.dataServices.ExerciseWithParametersService;

import java.util.List;

public class WorkoutDetails extends VerticalLayout {

    private Grid<ExerciseWithParameters> exerciseWithParametersGrid = new Grid<>();
    private ExerciseWithParametersService exerciseWithParametersService = ExerciseWithParametersService.getInstance();

    public WorkoutDetails(){
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button closeButton = new Button("Close");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getExerciseName).setHeader("Exercise").setWidth("40%").setResizable(true);
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getNumberOfSeries).setHeader("Series");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getNumberOfRepetitions).setHeader("Repetitions");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getPauseTime).setHeader("Pause time");
        closeButton.addClickListener(event -> setDetailedWorkoutGridVisibility(null));
        buttonsLayout.add(closeButton);
        add(exerciseWithParametersGrid, buttonsLayout);
        setSizeFull();
        setVisible(true);
    }

    public void fillGrid(Workout workout){
        exerciseWithParametersGrid.setItems(workout.getExercisesWithSeriesRepetitionsBreaks());
    }

    public void setDetailedWorkoutGridVisibility(Workout workout) {
        if (workout == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
