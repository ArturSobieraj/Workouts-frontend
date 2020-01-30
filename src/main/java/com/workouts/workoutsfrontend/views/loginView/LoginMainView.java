package com.workouts.workoutsfrontend.views.loginView;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.Dto.User;
import com.workouts.workoutsfrontend.dataServices.UserService;
import com.workouts.workoutsfrontend.views.dashboardView.DashboardMainView;

@Route("")
public class LoginMainView extends VerticalLayout {

    private UserService userService = UserService.getInstance();

    private TextField email = new TextField("Email");
    private PasswordField password = new PasswordField("Hasło");

    public LoginMainView() {

        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label("Workout Planner");

        VerticalLayout vl2 = new VerticalLayout();
        email.setWidth("30%");
        password.setWidth("30%");
        vl2.add(email);
        vl2.add(password);
        vl2.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button login = new Button("Logowanie");
        login.setSizeUndefined();
        login.addClickListener(event -> logingEvent(login));
        Button register = new Button("Rejestracja");
        register.setSizeUndefined();
        register.addClickListener(event -> registerEvent(register));
        horizontalLayout.add(register);
        horizontalLayout.add(login);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        verticalLayout.add(title);
        verticalLayout.add(vl2);
        verticalLayout.add(horizontalLayout);
        verticalLayout.setAlignItems(Alignment.CENTER);
        add(verticalLayout);
    }

    private void logingEvent(Button login) {
        if (!email.getValue().isEmpty() && !password.getValue().isEmpty()) {
            if (userService.getEnteringUser(email.getValue()).getMail() != null) {
                if (userService.getEnteringUser(email.getValue()).getPassword().equals(password.getValue())) {
                    login.getUI().ifPresent(ui -> ui.navigate(DashboardMainView.class, email.getValue()));
                } else {
                    Notification.show("Błędne hasło", 2000, Notification.Position.MIDDLE);
                }
            } else {
                Notification.show("Nie znaleziono użytkownika", 2000, Notification.Position.MIDDLE);
            }
        } else {
            Notification.show("Fields cannot be empty", 3000, Notification.Position.MIDDLE);
        }
    }

    private void registerEvent(Button register) {
        register.getUI().ifPresent(ui -> ui.navigate("register"));
    }
}
