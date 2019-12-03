package com.workouts.workoutsfrontend.loginView;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginMainView extends VerticalLayout {

    String string = new String("dasdas");

    public LoginMainView() {

        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label("Workout Planner");

        VerticalLayout vl2 = new VerticalLayout();
        TextField email = new TextField("Email");
        email.setWidth("40%");
        PasswordField password = new PasswordField("Password");
        password.setWidth("40%");
        vl2.add(email);
        vl2.add(password);
        vl2.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button login = new Button("Login");
        login.setWidth("50%");
        Button register = new Button("Register");
        register.setSizeFull();
        horizontalLayout.add(login);
        horizontalLayout.add(register);
        horizontalLayout.setAlignItems(Alignment.CENTER);

        verticalLayout.add(title);
        verticalLayout.add(vl2);
        verticalLayout.add(horizontalLayout);
        verticalLayout.setAlignItems(Alignment.CENTER);
        add(verticalLayout);
    }
}
