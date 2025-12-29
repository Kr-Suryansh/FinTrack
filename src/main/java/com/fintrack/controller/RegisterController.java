package com.fintrack.controller;

import com.fintrack.App;
import com.fintrack.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    private final AuthService authService;

    public RegisterController() {
        this.authService = new AuthService();
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        errorLabel.setVisible(false);
        successLabel.setVisible(false);

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        try {
            boolean success = authService.register(username, password);
            if (success) {
                successLabel.setText("Registration successful! You can now login.");
                successLabel.setVisible(true);
                usernameField.clear();
                passwordField.clear();
                confirmPasswordField.clear();
            } else {
                showError("Username already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error registering user. Connection failure?");
        }
    }

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("view/login");
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
