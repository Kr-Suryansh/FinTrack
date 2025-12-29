package com.fintrack.controller;

import com.fintrack.App;
import com.fintrack.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainLayoutController {

    @FXML
    private Label headerTitle;

    @FXML
    private Label userLabel;

    @FXML
    private BorderPane contentArea;

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() != null) {
            userLabel.setText(Session.getCurrentUser().getUsername());
        }
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        loadView("dashboard", "Home / Dashboard");
    }

    @FXML
    private void showLedger() {
        loadView("transaction_history", "Home / Ledger");
    }

    @FXML
    private void showBudget() {
        loadView("budget", "Home / Budget");
    }

    @FXML
    private void showReports() {
        loadView("reports", "Home / Reports");
    }

    @FXML
    private void showCategories() {
        loadView("categories", "Home / Categories");
    }

    @FXML
    private void handleLogout() throws IOException {
        Session.clear();
        App.setRoot("view/login");
    }

    private void loadView(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/fintrack/view/" + fxml + ".fxml"));
            Parent view = loader.load();
            contentArea.setCenter(view);
            headerTitle.setText(title);
        } catch (IOException e) {
            e.printStackTrace();
            contentArea.setCenter(new Label("Error loading view: " + fxml + "\n" + e.getMessage()));
        }
    }
}
