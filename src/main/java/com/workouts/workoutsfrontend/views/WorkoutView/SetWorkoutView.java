package com.workouts.workoutsfrontend.views.WorkoutView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.dataServices.Dto.Workout;
import com.workouts.workoutsfrontend.dataServices.ExerciseWithParametersService;
import com.workouts.workoutsfrontend.dataServices.WorkoutService;

import java.time.LocalDate;

@Route("submit")
public class SetWorkoutView extends VerticalLayout {

    private TextField workoutName = new TextField("Workout name");
    private TextField workoutDate = new TextField("Workout date [YYYY-MM-DD]");
    private Label title = new Label("Set Workout");
    private Button back = new Button("Back");
    private Button submit = new Button("Submit");
    private ExerciseWithParametersService exerciseWithParametersService = ExerciseWithParametersService.getInstance();
    private WorkoutService workoutService = WorkoutService.getInstance();

    public SetWorkoutView() {

        HorizontalLayout dataLayout = new HorizontalLayout(workoutName, workoutDate);
        back.addClickListener(event -> goBack());
        submit.addClickListener(event -> submitWorkout());
        HorizontalLayout buttonLayout = new HorizontalLayout(back, submit);
        add(title, dataLayout, buttonLayout);
        setAlignItems(Alignment.CENTER);
    }

    private void goBack() {
        getUI().ifPresent(ui -> ui.navigate(NewWorkoutView.class, "parameter"));
    }

    private void submitWorkout() {
        if (workoutName.getValue() != null && workoutDate.getValue() != null) {
            if (workoutService.getWorkoutList().stream().filter(workout -> workout.getWorkoutName().equals(workoutName.getEmptyValue())).count() == 0) {
            try {
                    workoutService.addWorkout(new Workout(workoutName.getValue(), exerciseWithParametersService.getExercisesForNewWorkout(), LocalDate.parse(workoutDate.getValue())));
                    exerciseWithParametersService.setClearList();
                    Notification.show("Workout created", 3000, Notification.Position.MIDDLE);
                    getUI().ifPresent(ui -> ui.navigate("dashboard"));
                } catch(Exception e){
                    Notification.show("Error: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                    getUI().ifPresent(ui -> ui.navigate("dashboard"));
                }
            } else {
                Notification.show("Workouts cannot have same name!", 3000, Notification.Position.MIDDLE);
            }
        } else {
            Notification.show("No field can be empty!");
        }
    }
}
