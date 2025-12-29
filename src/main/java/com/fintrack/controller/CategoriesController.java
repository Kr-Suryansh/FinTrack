package com.fintrack.controller;

import com.fintrack.dao.CategoryDAO;
import com.fintrack.model.Category;
import com.fintrack.util.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriesController {

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ListView<String> incomeList;
    @FXML
    private ListView<String> expenseList;
    @FXML
    private Label messageLabel;

    private final CategoryDAO categoryDAO;

    public CategoriesController() {
        this.categoryDAO = new CategoryDAO();
    }

    @FXML
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList("INCOME", "EXPENSE"));
        loadData();
    }

    private void loadData() {
        if (Session.getCurrentUser() != null) {
            List<Category> all = categoryDAO.findAll(Session.getCurrentUser().getId());

            incomeList.getItems().setAll(
                    all.stream().filter(c -> "INCOME".equalsIgnoreCase(c.getType()))
                            .map(Category::getName).collect(Collectors.toList()));

            expenseList.getItems().setAll(
                    all.stream().filter(c -> "EXPENSE".equalsIgnoreCase(c.getType()))
                            .map(Category::getName).collect(Collectors.toList()));
        }
    }

    @FXML
    private void handleAdd() {
        String name = nameField.getText();
        String type = typeComboBox.getValue();

        if (name.isEmpty() || type == null) {
            messageLabel.setText("Name and Type required.");
            messageLabel.setVisible(true);
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Category cat = new Category(name, type, null);
        cat.setUserId(Session.getCurrentUser().getId());
        categoryDAO.save(cat);

        nameField.clear();
        loadData();
        messageLabel.setText("Category added.");
        messageLabel.setVisible(true);
        messageLabel.setStyle("-fx-text-fill: green;");
    }
}
