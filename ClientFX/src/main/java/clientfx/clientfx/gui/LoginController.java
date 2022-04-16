package clientfx.clientfx.gui;

import app.App;
import app.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import project.services.IServices;

public class LoginController {
    @FXML
    Button loginButton;
    @FXML
    TextField textFieldEmail;
    @FXML
    PasswordField textFieldPassword;
    @FXML
    Label warnLabel;
    @FXML
    Button registerButton;
    private IServices service;

    /**
     * Initialize the application.controller, add listeners for key presses.
     */
    @FXML
    public void initialize() {
        textFieldEmail.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
        textFieldPassword.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
    }

    /**
     * Click handler for the login button.
     * If the login information is correct, it enters the application, otherwise it
     * displays an error message.
     */
    public void onLoginButtonClick() {
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();
        Integer userId = service.login(email, password);
        if (userId == null) {
            warnLabel.setVisible(true);
            return;
        }
        App.changeSceneToMainWindow(service);
    }

    /**
     * Handler for the enter key.
     * When the enter key is pressed, collect login info and try to login.
     */
    private void enterKeyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.ENTER) {
            onLoginButtonClick();
        }
    }

    /**
     * Switch to register window
     */
    public void onRegisterButtonClick() {
        App.changeSceneToRegister(service);
    }

    /**
     * Initialize the application.service
     */
    public void setService(Service service) {
        this.service = service;
    }
}
