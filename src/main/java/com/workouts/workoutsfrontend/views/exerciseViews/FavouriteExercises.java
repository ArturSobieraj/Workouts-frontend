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
import com.workouts.workoutsfrontend.Dto.FavouriteExercise;
import com.workouts.workoutsfrontend.dataServices.ExerciseService;
import com.workouts.workoutsfrontend.dataServices.WorkoutService;
import com.workouts.workoutsfrontend.facades.UsersFacade;
import com.workouts.workoutsfrontend.views.dashboardView.DashboardMainView;


@Route("favourites")
public class FavouriteExercises extends VerticalLayout implements HasUrlParameter<String> {

    private Grid<FavouriteExercise> favouriteExercisesGrid = new Grid<>();
    private UsersFacade usersFacade = new UsersFacade();
    private ExerciseService exerciseService = ExerciseService.getInstance();
    private WorkoutService workoutService = WorkoutService.getInstance();
    private final String noSelectedNotification = "No exercise selected";

    public FavouriteExercises() {
        Label title = new Label("Favourite Exercises");
        Button back = new Button("Back");
        Button delete = new Button("Delete from Favourites");
        Button showDescription = new Button("Show description");
        back.addClickListener(event -> backAction(back));
        showDescription.addClickListener(event -> showDescription());
        delete.addClickListener(event -> deleteFromFavourites());
        favouriteExercisesGrid.addColumn(favouriteExercise -> favouriteExercise.getExercise().getExerciseName()).setHeader("Exercise Name");
        favouriteExercisesGrid.addColumn(favouriteExercise -> favouriteExercise.getExercise().getCategory()).setHeader("Category");
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
            exerciseService.addNewFavouriteExercise(workoutService.getUserMail(), exerciseService.getExerciseByName(parameter).getId());
            refresh();
        }
    }

    private void backAction(Button back) {
        back.getUI().ifPresent(ui -> ui.navigate(DashboardMainView.class, "zero parameter"));
    }

    private void refresh() {
        favouriteExercisesGrid.setItems(exerciseService.getFavouriteExercisesList(workoutService.getUserMail()));
    }

    private void deleteFromFavourites() {
        if (favouriteExercisesGrid.asSingleSelect().getValue() != null) {
            exerciseService.deleteFromFavourites(favouriteExercisesGrid.asSingleSelect().getValue().getId());
            refresh();
        } else {
            Notification.show(noSelectedNotification, 2000, Notification.Position.MIDDLE);
        }
    }

    private void showDescription() {
        if (favouriteExercisesGrid.asSingleSelect().getValue() != null) {
            getUI().ifPresent(ui -> ui.navigate(SingleExerciseView.class, favouriteExercisesGrid.asSingleSelect().getValue().getExercise().getExerciseName()));
        } else {
            Notification.show(noSelectedNotification, 2000, Notification.Position.MIDDLE);
        }
    }
}
