package com.fintrack.controller;

import com.fintrack.dao.CategoryDAO;
import com.fintrack.dao.TransactionDAO;
import com.fintrack.model.Category;
import com.fintrack.model.Transaction;
import com.fintrack.util.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionFormController {

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Label errorLabel;

    private final TransactionDAO transactionDAO;
    private final CategoryDAO categoryDAO;

    public TransactionFormController() {
        this.transactionDAO = new TransactionDAO();
        this.categoryDAO = new CategoryDAO();
    }

    @FXML
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList("INCOME", "EXPENSE"));
        typeComboBox.setOnAction(e -> updateCategories());

        // Default to loading all or wait for type selection
    }

    private void updateCategories() {
        String type = typeComboBox.getValue();
        if (type != null) {
            List<Category> categories = categoryDAO.findAll().stream()
                    .filter(c -> c.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
            categoryComboBox.setItems(FXCollections.observableArrayList(
                    categories.stream().map(Category::getName).collect(Collectors.toList())));
        }
    }

    @FXML
    private void handleSave() {
        try {
            String type = typeComboBox.getValue();
            String amountText = amountField.getText();
            String category = categoryComboBox.getValue();
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String description = descriptionArea.getText();

            if (type == null || amountText.isEmpty() || category == null || date == null) {
                errorLabel.setText("Please fill all required fields.");
                errorLabel.setVisible(true);
                return;
            }

            double amount = Double.parseDouble(amountText);

            Transaction transaction = new Transaction(
                    Session.getCurrentUser().getId(),
                    type,
                    amount,
                    category,
                    description,
                    date);

            transactionDAO.save(transaction);

            // Close window
            Stage stage = (Stage) amountField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid amount format.");
            errorLabel.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error saving transaction.");
            errorLabel.setVisible(true);
        }
    }
}
