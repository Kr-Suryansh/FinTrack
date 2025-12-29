package com.fintrack.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label balanceLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private javafx.scene.control.Label expenseLabel;

    @FXML
    private javafx.scene.control.TableView<com.fintrack.model.Transaction> recentTransactionsTable;
    @FXML
    private javafx.scene.control.TableColumn<com.fintrack.model.Transaction, java.util.Date> dateColumn;
    @FXML
    private javafx.scene.control.TableColumn<com.fintrack.model.Transaction, String> descColumn;
    @FXML
    private javafx.scene.control.TableColumn<com.fintrack.model.Transaction, Double> amountColumn;

    private com.fintrack.dao.TransactionDAO transactionDAO;

    public DashboardController() {
    }

    @FXML
    public void initialize() {
        System.out.println("DashboardController initialized.");
        try {
            this.transactionDAO = new com.fintrack.dao.TransactionDAO();

            if (dateColumn == null)
                System.err.println("dateColumn is NULL");
            if (descColumn == null)
                System.err.println("descColumn is NULL");
            if (amountColumn == null)
                System.err.println("amountColumn is NULL");

            // Setup columns
            dateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));
            descColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("description"));
            amountColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("amount"));

            com.fintrack.model.User user = com.fintrack.util.Session.getCurrentUser();
            if (user != null) {
                double income = transactionDAO.getTotalIncome(user.getId());
                double expense = transactionDAO.getTotalExpense(user.getId());
                double balance = income - expense;

                balanceLabel.setText(String.format("Rs. %.2f", balance));
                incomeLabel.setText(String.format("Rs. %.2f", income));
                expenseLabel.setText(String.format("Rs. %.2f", expense));

                // Populate Recent Transactions
                java.util.List<com.fintrack.model.Transaction> all = transactionDAO.findByUserId(user.getId());
                // Assuming findByUserId sorts by date desc
                java.util.List<com.fintrack.model.Transaction> recent = all.stream().limit(5)
                        .collect(java.util.stream.Collectors.toList());
                recentTransactionsTable.setItems(javafx.collections.FXCollections.observableArrayList(recent));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openTransactionForm() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/fintrack/view/transaction_form.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Add Transaction");
            stage.setScene(new javafx.scene.Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.showAndWait();
            // Refresh dashboard data
            initialize();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
