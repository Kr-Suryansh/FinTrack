package com.fintrack.controller;

import com.fintrack.dao.TransactionDAO;
import com.fintrack.model.Transaction;
import com.fintrack.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionHistoryController {

    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Date> dateColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;

    private final TransactionDAO transactionDAO;

    public TransactionHistoryController() {
        this.transactionDAO = new TransactionDAO();
    }

    @FXML
    public void initialize() {
        // Format Date
        dateColumn.setCellFactory(column -> {
            return new TableCell<Transaction, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
        });

        // Format Amount and Color based on Type
        amountColumn.setCellFactory(column -> {
            return new TableCell<Transaction, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(String.format("Rs. %.2f", item));
                        Transaction t = getTableView().getItems().get(getIndex());
                        if ("INCOME".equalsIgnoreCase(t.getType())) {
                            setStyle("-fx-text-fill: green;");
                        } else {
                            setStyle("-fx-text-fill: red;");
                        }
                    }
                }
            };
        });

        // Context Menu for Delete
        javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();
        javafx.scene.control.MenuItem deleteItem = new javafx.scene.control.MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                transactionDAO.delete(selected.getId());
                loadData();
            }
        });
        contextMenu.getItems().add(deleteItem);
        transactionTable.setContextMenu(contextMenu);

        loadData();
    }

    public void loadData() {
        if (Session.getCurrentUser() != null) {
            List<Transaction> transactions = transactionDAO.findByUserId(Session.getCurrentUser().getId());
            ObservableList<Transaction> data = FXCollections.observableArrayList(transactions);
            transactionTable.setItems(data);
        }
    }
}
