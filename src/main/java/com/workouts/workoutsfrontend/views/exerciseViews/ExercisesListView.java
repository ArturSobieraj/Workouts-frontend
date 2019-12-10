package com.workouts.workoutsfrontend.views.exerciseViews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.dataServices.Dto.Exercise;
import com.workouts.workoutsfrontend.dataServices.ExerciseService;

import java.util.List;
import java.util.stream.Collectors;

@Route("exercisesList")
public class ExercisesListView extends VerticalLayout {

    private Button absButton = new Button("ABS");
    private Button armsButton = new Button("Arms");
    private Button backButton = new Button("Back");
    private Button calvesButton = new Button("Calves");
    private Button chestButton = new Button("Chest");
    private Button legsButton = new Button("Legs");
    private Button shouldersButton = new Button("Shoulders");
    private Button getBackButton = new Button("Back");
    private Grid<Exercise> exerciseGrid = new Grid<>();
    private ExerciseService exerciseService = ExerciseService.getInstance();

    public ExercisesListView() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(absButton, armsButton, backButton, calvesButton, chestButton, legsButton, shouldersButton);
        absButton.addClickListener(event -> showSelectedExercises(absButton.getText()));
        armsButton.addClickListener(event -> showSelectedExercises(armsButton.getText()));
        backButton.addClickListener(event -> showSelectedExercises(backButton.getText()));
        calvesButton.addClickListener(event -> showSelectedExercises(calvesButton.getText()));
        chestButton.addClickListener(event -> showSelectedExercises(chestButton.getText()));
        legsButton.addClickListener(event -> showSelectedExercises(legsButton.getText()));
        shouldersButton.addClickListener(event -> showSelectedExercises(shouldersButton.getText()));
        getBackButton.addClickListener(event -> goBackAction(getBackButton));
        exerciseGrid.setVisible(false);
        exerciseGrid.addColumn(Exercise::getExerciseName).setHeader("Exercise name");
        exerciseGrid.setSizeFull();
        add(buttonsLayout, exerciseGrid, getBackButton);
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        exerciseGrid.asSingleSelect().addValueChangeListener(event -> getUI().ifPresent(ui -> ui.navigate(SingleExerciseView.class,
                exerciseGrid.asSingleSelect().getValue().getExerciseName())));

    }

    private void showSelectedExercises(String category) {
        exerciseGrid.setVisible(true);
        switch (category) {
            case "ABS":
                List<Exercise> absList = exerciseService.getExerciseList().stream()
                        .filter(exercise -> exercise.getCategory().equals("Abs"))
                        .collect(Collectors.toList());
                exerciseGrid.setItems(absList);
                break;
            case "Arms":
                List<Exercise> armsList = exerciseService.getExerciseList().stream()
                        .filter(exercise -> exercise.getCategory().equals("Arms"))
                        .collect(Collectors.toList());
                exerciseGrid.setItems(armsList);
                break;
            case "Back":
                List<Exercise> backList = exerciseService.getExerciseList().stream()
                        .filter(exercise -> exercise.getCategory().equals("Back"))
                        .collect(Collectors.toList());
                exerciseGrid.setItems(backList);
                break;
            case "Calves":
                List<Exercise> calvesList = exerciseService.getExerciseList().stream()
                        .filter(exercise -> exercise.getCategory().equals("Calves"))
                        .collect(Collectors.toList());
                exerciseGrid.setItems(calvesList);
                break;
            case "Chest":
                List<Exercise> chestList = exerciseService.getExerciseList().stream()
                        .filter(exercise -> exercise.getCategory().equals("Chest"))
                        .collect(Collectors.toList());
                exerciseGrid.setItems(chestList);
                break;
            case "Legs":
                List<Exercise> legsList = exerciseService.getExerciseList().stream()
                        .filter(exercise -> exercise.getCategory().equals("Legs"))
                        .collect(Collectors.toList());
                exerciseGrid.setItems(legsList);
                break;
            case "Shoulders":
                List<Exercise> shouldersList = exerciseService.getExerciseList().stream()
                        .filter(exercise -> exercise.getCategory().equals("Shoulders"))
                        .collect(Collectors.toList());
                exerciseGrid.setItems(shouldersList);
                break;
        }
    }

    private void goBackAction(Button backButton) {
        backButton.getUI().ifPresent(ui -> ui.navigate("dashboard"));
    }
}
