package com.workouts.workoutsfrontend.views.WorkoutView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.Dto.ExerciseWithParameters;
import com.workouts.workoutsfrontend.Dto.Workout;
import com.workouts.workoutsfrontend.dataServices.ExerciseWithParametersService;
import com.workouts.workoutsfrontend.dataServices.WorkoutService;
import com.workouts.workoutsfrontend.views.dashboardView.DashboardMainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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
        if (!workoutName.getValue().isEmpty()) {
            Workout newWorkout = new Workout();
            if (workoutService.getWorkoutId() != null) {
                newWorkout.setId(workoutService.getWorkoutId());
                newWorkout.setWorkoutName(workoutName.getValue());
                newWorkout.setExercisesWithSeriesRepetitionsBreaks(exerciseWithParametersService.getWorkoutExercises(workoutService.getWorkoutId()));
                newWorkout.setTrainingDate(LocalDate.parse(workoutDate.getValue()));
                workoutService.updateWorkout(newWorkout);
                workoutService.setWorkoutId(null);
                redirection();
            } else {
                try {
                    workoutService.addWorkout(workoutName.getValue(), LocalDate.parse(workoutDate.getValue()));
                    redirection();
                } catch (DateTimeParseException e) {
                    Notification.show("Bad time format!", 3000, Notification.Position.MIDDLE);
                }
            }
        } else {
            Notification.show("Name field can't be empty!", 2000, Notification.Position.MIDDLE);
        }
    }

    private void redirection() {
        Notification.show("Workout has been created", 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate(DashboardMainView.class, "zero parameter"));
    }
}

