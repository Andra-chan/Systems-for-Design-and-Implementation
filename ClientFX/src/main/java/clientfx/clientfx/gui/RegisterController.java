package clientfx.clientfx.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class RegisterController {
    Service service;

    @FXML
    TextField textFieldFirstName;

    @FXML
    TextField textFieldLastName;

    @FXML
    TextField textFieldEmail;

    @FXML
    PasswordField textFieldPassword;

    @FXML
    PasswordField textFieldConfirmPassword;

    @FXML
    Button returnButton;

    @FXML
    Button registerButton;

    @FXML
    Label errorLabel;

    /**
     * Initialize the application.controller. Add 'enter' key listeners to text fields.
     */
    @FXML
    public void initialize() {
        errorLabel.setStyle("-fx-text-fill: red");
        errorLabel.setVisible(false);
        textFieldFirstName.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
        textFieldLastName.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
        textFieldEmail.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
        textFieldPassword.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
        textFieldConfirmPassword.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
    }

    /**
     * Click handler for the register button
     * <p>
     * When the register button is pressed, collect registration info from text
     * fields and attempt to register a new user.
     * <p>
     * If a new user has been created, the window will switch from register window
     * to a login window, otherwise an error message is displayed.
     */
    public void onRegisterButtonPress() {
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();
        String confirmPassword = textFieldConfirmPassword.getText();

        if (password.isBlank()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Password cannot be blank");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setVisible(true);
            errorLabel.setText("Passwords do not match");
            return;
        }

        try {
            var newUser = new User(firstName, lastName, email, password);
            var user = service.addUser(newUser);
            if (user == null) {
                errorLabel.setStyle("-fx-text-fill: green");
                errorLabel.setText("Register successful");
                errorLabel.setVisible(true);
            }
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
            errorLabel.setVisible(true);
        }

    }

    /**
     * Handler for the enter key.
     * When the enter key is pressed, collect registration info and try to register
     * a new user.
     */
    private void enterKeyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.ENTER) {
            onRegisterButtonPress();
        }
    }

    public void onReturnButtonPress() {
        App.changeSceneToLogin(service);
    }

    /**
     * Initialize the application.service
     */
    public void setService(Service service) {
        this.service = service;
    }

}
