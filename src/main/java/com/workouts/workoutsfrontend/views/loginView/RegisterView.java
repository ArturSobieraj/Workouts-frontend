package com.workouts.workoutsfrontend.views.loginView;

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
import com.workouts.workoutsfrontend.Dto.User;
import com.workouts.workoutsfrontend.dataServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
public class RegisterView extends VerticalLayout {

    private UserService userService = UserService.getInstance();
    private TextField newUserEmail = new TextField("Wpisz email");
    private PasswordField newUserPassword = new PasswordField("Wpisz hasło");
    private PasswordField confirmPassword = new PasswordField("Potwierdź hasło");

    public RegisterView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label registerLabel = new Label("Rejestracja nowego użytkownika");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button register = new Button("Rejestracja");
        register.addClickListener(event -> proceedRegistration(register));
        Button back = new Button("Powrót");
        back.addClickListener(event -> goBack(back));
        horizontalLayout.add(back);
        horizontalLayout.add(register);

        verticalLayout.add(registerLabel, newUserEmail, newUserPassword, confirmPassword, horizontalLayout);
        verticalLayout.setAlignItems(Alignment.CENTER);
        add(verticalLayout);
    }

    private void proceedRegistration(Button register) {
        if (newUserPassword.getValue().equals(confirmPassword.getValue())) {
            if (userService.getUsers().stream().filter(user -> user.getMail().equals(newUserEmail.getValue())).count() != 0) {
                Notification.show("Użytkownik już istnieje!", 2000, Notification.Position.MIDDLE);
            } else {
                userService.addUser(new User(newUserEmail.getValue(), newUserPassword.getValue()));
                Notification.show("Konto utworzone", 2000, Notification.Position.MIDDLE);
                register.getUI().ifPresent(ui -> ui.navigate(""));
            }
        } else {
            Notification.show("Błędne potwierdzenie hasła", 2000, Notification.Position.MIDDLE);
        }
    }

    private void goBack(Button back) {
        back.getUI().ifPresent(ui -> ui.navigate(""));
    }
}
