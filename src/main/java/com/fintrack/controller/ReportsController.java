package com.fintrack.controller;

import com.fintrack.dao.TransactionDAO;
import com.fintrack.model.Transaction;
import com.fintrack.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportsController {

    @FXML
    private PieChart expensePieChart;
    @FXML
    private BarChart<String, Number> incomeExpenseBarChart;

    private final TransactionDAO transactionDAO;

    public ReportsController() {
        this.transactionDAO = new TransactionDAO();
    }

    @FXML
    public void initialize() {
        if (Session.getCurrentUser() != null) {
            loadCharts();
        }
    }

    private void loadCharts() {
        List<Transaction> transactions = transactionDAO.findByUserId(Session.getCurrentUser().getId());

        // Pie Chart: Expenses by Category
        Map<String, Double> expensesByCategory = transactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        expensesByCategory.forEach((cat, amount) -> pieData.add(new PieChart.Data(cat, amount)));
        expensePieChart.setData(pieData);

        // Bar Chart: Total Income vs Expense
        double totalIncome = transactions.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();
        double totalExpense = transactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Financial Overview");
        series.getData().add(new XYChart.Data<>("Income", totalIncome));
        series.getData().add(new XYChart.Data<>("Expenses", totalExpense));

        incomeExpenseBarChart.getData().clear();
        incomeExpenseBarChart.getData().add(series);
    }
}
