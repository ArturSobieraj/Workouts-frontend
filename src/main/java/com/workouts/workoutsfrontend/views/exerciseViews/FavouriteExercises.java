package com.workouts.workoutsfrontend.views.exerciseViews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.dataServices.Dto.Exercise;
import com.workouts.workoutsfrontend.dataServices.ExerciseService;

@Route("favourites")
public class FavouriteExercises extends VerticalLayout implements HasUrlParameter<String> {

    private Grid<Exercise> favouriteExercisesGrid = new Grid<>();
    private ExerciseService exerciseService = ExerciseService.getInstance();
    private final String noSelectedNotification = "No exercise selected";

    public FavouriteExercises() {
        Label title = new Label("Favourite Exercises");
        Button back = new Button("Back");
        Button delete = new Button("Delete from Favourites");
        Button showDescription = new Button("Show description");
        back.addClickListener(event -> backAction(back));
        showDescription.addClickListener(event -> showDescription());
        delete.addClickListener(event -> deleteFromFavourites());
        favouriteExercisesGrid.addColumn(Exercise::getExerciseName).setHeader("Exercise Name");
        favouriteExercisesGrid.addColumn(Exercise::getCategory).setHeader("Category");
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(back, delete, showDescription);
        add(title, favouriteExercisesGrid, buttonsLayout);
        setAlignItems(Alignment.CENTER);
        refresh();
        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (!parameter.equals("zeroParameter")) {
            Exercise addedExercise = exerciseService.getExerciseByName(parameter);
            exerciseService.addNewFavouriteExercise(addedExercise);
            refresh();
        }
    }

    private void backAction(Button back) {
        back.getUI().ifPresent(ui -> ui.navigate("dashboard"));
    }

    private void refresh() {
        favouriteExercisesGrid.setItems(exerciseService.getFavouriteExercisesList());
    }

    private void deleteFromFavourites() {
        if (favouriteExercisesGrid.asSingleSelect().getValue() != null) {
            exerciseService.deleteFromFavourites(favouriteExercisesGrid.asSingleSelect().getValue());
            refresh();
        } else {
            Notification.show(noSelectedNotification, 1000, Notification.Position.MIDDLE);
        }
    }

    private void showDescription() {
        if (favouriteExercisesGrid.asSingleSelect().getValue() != null) {
            getUI().ifPresent(ui -> ui.navigate(SingleExerciseView.class, favouriteExercisesGrid.asSingleSelect().getValue().getExerciseName()));
        } else {
            Notification.show(noSelectedNotification, 1000, Notification.Position.MIDDLE);
        }
    }
}
