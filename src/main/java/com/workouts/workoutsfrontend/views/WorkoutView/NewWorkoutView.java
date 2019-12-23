package com.workouts.workoutsfrontend.views.WorkoutView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.Dto.Exercise;
import com.workouts.workoutsfrontend.Dto.ExerciseWithParameters;
import com.workouts.workoutsfrontend.Dto.Workout;
import com.workouts.workoutsfrontend.dataServices.ExerciseService;
import com.workouts.workoutsfrontend.dataServices.ExerciseWithParametersService;
import com.workouts.workoutsfrontend.dataServices.WorkoutService;
import com.workouts.workoutsfrontend.views.exerciseViews.FavouriteExercises;
import org.springframework.beans.factory.annotation.Autowired;


@Route("workout")
public class NewWorkoutView extends VerticalLayout implements HasUrlParameter<String> {

    private WorkoutService workoutService = WorkoutService.getInstance();
    private final String LABEL_DESC = "Exercise name: ";
    private ExerciseWithParametersService exerciseWithParametersService = ExerciseWithParametersService.getInstance();
    private ExerciseService exerciseService = ExerciseService.getInstance();
    private Grid<ExerciseWithParameters> exerciseWithParametersGrid = new Grid<>();
    private String exerciseName = "";
    private Label newWorkout = new Label("Create new Workout");
    private Label name = new Label(LABEL_DESC);
    private TextField series = new TextField("Series: ");
    private TextField repetitions = new TextField("Number of repetitions: ");
    private TextField pauses = new TextField("Pause time [s]: ");
    private Button addExercise = new Button("Add Exercise");
    private Button submit = new Button("Create workout");
    private Button goToFavourites = new Button("Favourites");
    private Button exercisesList = new Button("Exercises list");
    private Button editExercise = new Button("Edit");
    private Button deleteExercise = new Button("Delete exercise");

    public NewWorkoutView() {

        goToFavourites.addClickListener(event -> goToFavourites());
        exercisesList.addClickListener(event -> goToExercisesList());
        deleteExercise.addClickListener(event -> deleteExercise());
        editExercise.addClickListener(event -> editExercise());
        addExercise.addClickListener(event -> addExercise());
        submit.addClickListener(event -> createWorkout());
        HorizontalLayout parametersLayout = new HorizontalLayout(series, repetitions, pauses);
        parametersLayout.setAlignItems(Alignment.CENTER);

        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getExerciseName).setHeader("Exercise Name");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getNumberOfSeries).setHeader("Series");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getNumberOfRepetitions).setHeader("Repetitions");
        exerciseWithParametersGrid.addColumn(ExerciseWithParameters::getPauseTime).setHeader("Pause time");
        exerciseWithParametersGrid.setSizeUndefined();

        HorizontalLayout buttonsLayout = new HorizontalLayout(goToFavourites, exercisesList, deleteExercise, editExercise);
        buttonsLayout.setAlignItems(Alignment.CENTER);

        add(newWorkout, name, parametersLayout, addExercise, exerciseWithParametersGrid, buttonsLayout, submit);
        setAlignItems(Alignment.CENTER);
        refresh();
    }

    private void goToExercisesList() {
        getUI().ifPresent(ui -> ui.navigate("exercisesList"));
    }

    private void goToFavourites() {
        getUI().ifPresent(ui -> ui.navigate(FavouriteExercises.class, "zeroParameter"));
    }

    private void deleteExercise() {
        if (exerciseWithParametersGrid.asSingleSelect().getValue() != null) {
            exerciseWithParametersService.deleteAddedExercise(exerciseWithParametersGrid.asSingleSelect().getValue());
        } else {
            Notification.show("Nothing selected", 1000, Notification.Position.MIDDLE);
        }
        refresh();
    }

    private void refresh() {
        exerciseWithParametersGrid.setItems(exerciseWithParametersService.getExercisesForNewWorkout());
    }

    private void editExercise() {
        if (exerciseWithParametersGrid.asSingleSelect().getValue() != null) {
            ExerciseWithParameters editedExerciseWithParameters = exerciseWithParametersGrid.asSingleSelect().getValue();
            exerciseName = editedExerciseWithParameters.getExerciseName();
            series.setValue(editedExerciseWithParameters.getNumberOfSeries());
            repetitions.setValue(editedExerciseWithParameters.getNumberOfRepetitions());
            pauses.setValue(editedExerciseWithParameters.getPauseTime());
            exerciseWithParametersService.deleteAddedExercise(editedExerciseWithParameters);
        } else {
            Notification.show("Nothing selected", 1000, Notification.Position.MIDDLE);
        }
    }

    public void createWorkout() {
        getUI().ifPresent(ui -> ui.navigate("submit"));
    }

    private void addExercise() {
        if (exerciseService.getExerciseByName(exerciseName) != null && series.getValue() != null && repetitions.getValue() != null && pauses.getValue() != null) {
            exerciseWithParametersService.addNewExercise(new ExerciseWithParameters(exerciseService.getExerciseByName(exerciseName),
                    series.getValue(),
                    repetitions.getValue(),
                    pauses.getValue()));
        } else {
            Notification.show("Form fields cannot be null!", 1000, Notification.Position.MIDDLE);
        }
        series.clear();
        repetitions.clear();
        pauses.clear();
        name.setText(LABEL_DESC);
        refresh();
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (!parameter.equals("parameter")) {
            if (workoutService.getWorkoutList(workoutService.getUserMail()).stream().filter(workout -> workout.getWorkoutName().equals(parameter)).count() != 0) {
                for (Workout workout : workoutService.getWorkoutList(workoutService.getUserMail())) {
                    if (workout.getWorkoutName().equals(parameter)) {
                        exerciseWithParametersGrid.setItems(workout.getExercisesWithSeriesRepetitionsBreaks());
                        exerciseWithParametersService.setExercisesForNewWorkout(workout.getExercisesWithSeriesRepetitionsBreaks());
                        workoutService.deleteWorkout(workout);
                        refresh();
                    }
                }
            } else {
                Exercise exercise = exerciseService.getExerciseByName(parameter);
                exerciseName = exercise.getExerciseName();
                name.setText(LABEL_DESC + exercise.getExerciseName());
                refresh();
            }
        }
    }
}
