package com.workouts.workoutsfrontend.loginView;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.temoraryData.User;
import com.workouts.workoutsfrontend.temoraryData.UserService;

@Route("")
public class LoginMainView extends VerticalLayout {

    private UserService userService = UserService.getInstance();
    private Binder<User> binder = new Binder<>(User.class);
    private TextField email = new TextField("Email");
    private PasswordField password = new PasswordField("Password");

    public LoginMainView() {

        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label("Workout Planner");

        VerticalLayout vl2 = new VerticalLayout();
        email.setWidth("30%");
        password.setWidth("30%");
        vl2.add(email);
        vl2.add(password);
        vl2.setAlignItems(Alignment.CENTER);
        binder.bindInstanceFields(this);
        binder.setBean(new User());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button login = new Button("Login");
        login.setWidth("50%");
        login.addClickListener(event -> logingEvent(login));
        Button register = new Button("Register");
        register.setSizeFull();
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
        if (!userService.getEnteringUser(binder.getBean().getEmail()).isEmpty()) {
            if (userService.getEnteringUser(binder.getBean().getEmail()).get(0).getPassword().equals(binder.getBean().getPassword())) {
                login.getUI().ifPresent(ui -> ui.navigate("dashboard"));
            } else {
                Notification.show("Wrong password");
            }
        } else {
            Notification.show("User not found");
        }
    }

    private void registerEvent(Button register) {
        register.getUI().ifPresent(ui -> ui.navigate("register"));
    }
}
