package com.workouts.workoutsfrontend.loginView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.router.Route;
import com.workouts.workoutsfrontend.temoraryData.User;
import com.workouts.workoutsfrontend.temoraryData.UserService;

import java.util.stream.Collectors;

@Route("register")
public class RegisterView extends VerticalLayout {

    private UserService userService = UserService.getInstance();
    private Binder<User> binder = new Binder<>(User.class);
    @PropertyId("email")
    private TextField newUserEmail = new TextField("Enter email");
    @PropertyId("password")
    private PasswordField newUserPassword = new PasswordField("Enter password");
    private PasswordField confirmPassword = new PasswordField("Confirm password");

    public RegisterView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label registerLabel = new Label("New User Registration");
        binder.setBean(new User());
        binder.bindInstanceFields(this);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button register = new Button("Register");
        register.addClickListener(event -> proceedRegistration(register));
        Button back = new Button("Back");
        back.addClickListener(event -> goBack(back));
        horizontalLayout.add(back);
        horizontalLayout.add(register);

        verticalLayout.add(registerLabel, newUserEmail, newUserPassword, confirmPassword, horizontalLayout);
        verticalLayout.setAlignItems(Alignment.CENTER);
        add(verticalLayout);
    }

    private void proceedRegistration(Button register) {
        if (newUserPassword.getValue().equals(confirmPassword.getValue())) {
            if (userService.getUser().stream().filter(user -> user.getEmail().equals(binder.getBean().getEmail())).count() != 0) {
                Notification.show("User already exists!");
            } else {
                userService.addUser(binder.getBean());
                Notification.show("Account created", 2000, Notification.Position.MIDDLE);
                register.getUI().ifPresent(ui -> ui.navigate(""));
            }
        } else {
            Notification.show("Password confirmation incorrect");
        }
    }

    private void goBack(Button back) {
        back.getUI().ifPresent(ui -> ui.navigate(""));
    }
}
