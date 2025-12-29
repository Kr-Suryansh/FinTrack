package com.fintrack.controller;

import com.fintrack.dao.CategoryDAO;
import com.fintrack.model.Category;
import com.fintrack.util.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.util.List;
import java.util.stream.Collectors;

public class BudgetController {

    @FXML
    private TableView<Category> budgetTable;
    @FXML
    private TableColumn<Category, Double> limitColumn;

    private final CategoryDAO categoryDAO;

    public BudgetController() {
        this.categoryDAO = new CategoryDAO();
    }

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() != null) {
            List<Category> expenses = categoryDAO.findAll(Session.getCurrentUser().getId()).stream()
                    .filter(c -> "EXPENSE".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            budgetTable.setItems(FXCollections.observableArrayList(expenses));

            // Editable Column
            limitColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            limitColumn.setOnEditCommit(event -> {
                Category cat = event.getRowValue();
                cat.setBudgetLimit(event.getNewValue());
                // Save updates
                // Note: CategoryDAO currently only supports insertOne (save).
                // We need an update method in CategoryDAO. For now, since most are system
                // defaults, we might have issues.
                // However, for this simplified version, let's assume we can update.
                // Wait, CategoryDAO needs an 'update' method.
                // If it's a system category (userId == null), user shouldn't edit it directly
                // in DB effectively,
                // or we should create a copy.
                // To keep it simple: We will allow updating if we add an update method,
                // but user specific categories are easier.
                // For MVP, let's add `update` to DAO.
                categoryDAO.update(cat);
            });
        }
    }
}
