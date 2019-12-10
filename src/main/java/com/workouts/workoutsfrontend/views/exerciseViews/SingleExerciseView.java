package com.workouts.workoutsfrontend.views.exerciseViews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.dataServices.Dto.Exercise;
import com.workouts.workoutsfrontend.dataServices.ExerciseService;

@Route
public class SingleExerciseView extends VerticalLayout implements HasUrlParameter<String> {

    private ExerciseService exerciseService = ExerciseService.getInstance();
    private Label name = new Label();
    private Label description = new Label();
    private Label category = new Label();
    private Label muscles = new Label();
    private Label secondaryMuscles = new Label();
    private Label equipment = new Label();

    public SingleExerciseView() {

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button back = new Button("Back");
        back.addClickListener(event -> backAction(back));
        Button addToFavourites = new Button("Add to Favourites");
        Button addToWorkout = new Button("Add to Workout");
        buttonsLayout.add(back, addToFavourites, addToWorkout);

        add(name, description, category, muscles, secondaryMuscles, equipment, buttonsLayout);
        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {                     //tutaj trzeba będzie przekazać exerciseID lub name
        Exercise selectedExercise = exerciseService.getExerciseByName(parameter);
        name.setText(selectedExercise.getExerciseName());
        description.setText(selectedExercise.getDescription());
        category.setText(selectedExercise.getCategory());
        muscles.setText(selectedExercise.getMuscles().toString());
        secondaryMuscles.setText(selectedExercise.getSecondaryMuscles().toString());
        equipment.setText(selectedExercise.getEquipment().toString());
    }

    private void backAction(Button back) {
        back.getUI().ifPresent(ui -> ui.navigate("exercisesList"));
    }
}
