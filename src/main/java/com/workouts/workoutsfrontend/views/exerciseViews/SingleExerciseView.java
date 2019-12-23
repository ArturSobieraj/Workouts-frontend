package com.workouts.workoutsfrontend.views.exerciseViews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.Dto.Exercise;
import com.workouts.workoutsfrontend.dataServices.ExerciseService;
import com.workouts.workoutsfrontend.views.WorkoutView.NewWorkoutView;

@Route
public class SingleExerciseView extends VerticalLayout implements HasUrlParameter<String> {

    private ExerciseService exerciseService = ExerciseService.getInstance();
    private Label name = new Label();
    private Label description = new Label();
    private Label category = new Label();
    private Label muscles = new Label();

    public SingleExerciseView() {

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button back = new Button("Back");
        back.addClickListener(event -> backAction());
        Button addToFavourites = new Button("Add to Favourites");
        addToFavourites.addClickListener(event -> addToFavourites(addToFavourites));
        Button addToWorkout = new Button("Add to Workout");
        addToWorkout.addClickListener(event -> addToWorkout());
        buttonsLayout.add(back, addToFavourites, addToWorkout);

        add(name, description, category, muscles, buttonsLayout);
        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Exercise selectedExercise = exerciseService.getExerciseByName(parameter);
        if (selectedExercise.getId() == null) {
            Notification.show("Nie znaleziono ćwiczenia", 3000, Notification.Position.MIDDLE);
            backAction();
        } else {
            name.setText(selectedExercise.getExerciseName());
            description.setText(selectedExercise.getDescription());
            category.setText(selectedExercise.getCategory());
            muscles.setText(selectedExercise.getMuscles().toString());
        }
    }

    private void backAction() {
        getUI().ifPresent(ui -> ui.navigate("exercisesList"));
    }

    private void addToFavourites(Button button) {
        button.getUI().ifPresent(ui -> ui.navigate(FavouriteExercises.class, name.getText()));
    }

    private void addToWorkout() {
        getUI().ifPresent(ui -> ui.navigate(NewWorkoutView.class, name.getText()));
    }
}
