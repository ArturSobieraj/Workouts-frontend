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
    private ExerciseWithParametersService exerciseWithParametersService = ExerciseWithParametersService.getInstance();
    private ExerciseService exerciseService = ExerciseService.getInstance();
    private Grid<ExerciseWithParameters> exerciseWithParametersGrid = new Grid<>();
    private Label exerciseName = new Label();
    private Label newWorkout = new Label("Create new Workout");
    private Label nameLabel = new Label("Exercise name: ");
    private TextField series = new TextField("Series: ");
    private TextField repetitions = new TextField("Number of repetitions: ");
    private TextField pauses = new TextField("Pause time [s]: ");
    private Button addExercise = new Button("Add Exercise");
    private Button submit = new Button("Create workout");
    private Button goToFavourites = new Button("Favourites");
    private Button exercisesList = new Button("Exercises list");
    private Button editExercise = new Button("Edit");
    private Button deleteExercise = new Button("Delete exercise");
    private ExerciseWithParameters editingExercise = new ExerciseWithParameters();
    private boolean isEdited;

    public NewWorkoutView() { //problem z dodawaniem do treningu pustej listy ćwiczeń

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

        HorizontalLayout exerciseNameLayout = new HorizontalLayout();
        exerciseNameLayout.add(nameLabel, exerciseName);
        exerciseNameLayout.setAlignItems(Alignment.CENTER);
        HorizontalLayout buttonsLayout = new HorizontalLayout(goToFavourites, exercisesList, deleteExercise, editExercise);
        buttonsLayout.setAlignItems(Alignment.CENTER);

        add(newWorkout, exerciseNameLayout, parametersLayout, addExercise, exerciseWithParametersGrid, buttonsLayout, submit);
        setAlignItems(Alignment.CENTER);
    }

    private void goToExercisesList() {
        getUI().ifPresent(ui -> ui.navigate("exercisesList"));
    }

    private void goToFavourites() {
        getUI().ifPresent(ui -> ui.navigate(FavouriteExercises.class, "zeroParameter"));
    }

    private void deleteExercise() {
        if (exerciseWithParametersGrid.asSingleSelect().getValue() != null) {
            exerciseWithParametersService.deleteAddedExercise(exerciseWithParametersGrid.asSingleSelect().getValue().getId());
        } else {
            Notification.show("Nothing selected", 2000, Notification.Position.MIDDLE);
        }
        refresh();
    }

    private void refresh() {
        if(workoutService.getWorkoutId() == null) {
            exerciseWithParametersGrid.setItems(exerciseWithParametersService.getUsersExercises(workoutService.getUserMail()));
        } else {
            exerciseWithParametersGrid.setItems(exerciseWithParametersService.getWorkoutExercises(workoutService.getWorkoutId()));
        }
    }

    private void editExercise() {
        if (exerciseWithParametersGrid.asSingleSelect().getValue() != null) {
            editingExercise = exerciseWithParametersGrid.asSingleSelect().getValue();
            nameLabel.setText(editingExercise.getExerciseName());
            series.setValue(editingExercise.getNumberOfSeries());
            repetitions.setValue(editingExercise.getNumberOfRepetitions());
            pauses.setValue(editingExercise.getPauseTime());
            isEdited = true;
        } else {
            Notification.show("Nothing selected", 2000, Notification.Position.MIDDLE);
        }
    }

    public void createWorkout() {
        getUI().ifPresent(ui -> ui.navigate("submit"));
    }

    private void addExercise() {
        if (exerciseService.getExerciseByName(exerciseName.getText()) != null && !series.isEmpty() && !repetitions.isEmpty() && !pauses.isEmpty()) {
            if (!isEdited) {
                ExerciseWithParameters newExercise = new ExerciseWithParameters();
                newExercise.setExercises(exerciseService.getExerciseByName(exerciseName.getText()));
                newExercise.setNumberOfSeries(series.getValue());
                newExercise.setNumberOfRepetitions(repetitions.getValue());
                newExercise.setPauseTime(pauses.getValue());
                newExercise.setUserName(workoutService.getUserMail());
                exerciseWithParametersService.addNewExercise(newExercise);
            } else {
                editingExercise.setNumberOfSeries(series.getValue());
                editingExercise.setNumberOfRepetitions(repetitions.getValue());
                editingExercise.setPauseTime(pauses.getValue());
                if (workoutService.getWorkoutId() != null) {
                    editingExercise.setWorkout(workoutService.getWorkout(workoutService.getWorkoutId()));
                }
                exerciseWithParametersService.updateExercise(editingExercise);
            }
        } else {
            Notification.show("Form fields cannot be null!", 2000, Notification.Position.MIDDLE);
        }
        series.clear();
        repetitions.clear();
        pauses.clear();
        exerciseName.setText("");
        isEdited = false;
        refresh();
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (!parameter.equals("parameter")) {
            if (exerciseService.getExerciseByName(parameter).getId() != null) {
                exerciseName.setText(parameter);
            } else  {
                Notification.show("Wystąpił błąd", 2000, Notification.Position.MIDDLE);
            }
        }
        refresh();
    }
}
