package com.fintrack.controller;

import com.fintrack.App;
import com.fintrack.model.User;
import com.fintrack.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        try {
            User user = authService.login(username, password);
            if (user != null) {
                // Login Success
                // Login Success
                com.fintrack.util.Session.setCurrentUser(user);
                System.out.println("Login successful for user: " + user.getUsername());
                System.out.println("Login successful for user: " + user.getUsername());
                try {
                    App.setRoot("view/main_layout");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    showError("Failed to load Main Layout: " + ioException.getMessage());
                }
            } else {
                showError("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Login Error: " + e.getMessage());
        }
    }

    @FXML
    private void goToRegister() throws IOException {
        App.setRoot("view/register");
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setStyle("-fx-text-fill: red;");
    }
}
