package com.fintrack.dao;

import com.fintrack.model.Transaction;
import com.fintrack.util.DatabaseManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class TransactionDAO {
    private final MongoCollection<Transaction> transactionCollection;

    public TransactionDAO() {
        MongoDatabase database = DatabaseManager.getInstance().getDatabase();
        this.transactionCollection = database.getCollection("transactions", Transaction.class);
    }

    public void save(Transaction transaction) {
        transactionCollection.insertOne(transaction);
    }

    public List<Transaction> findByUserId(ObjectId userId) {
        return transactionCollection.find(eq("user_id", userId))
                .sort(eq("date", -1)) // Recent first
                .into(new ArrayList<>());
    }

    public void delete(ObjectId id) {
        transactionCollection.deleteOne(eq("_id", id));
    }

    public double getTotalIncome(ObjectId userId) {
        return calculateTotal(userId, "INCOME");
    }

    public double getTotalExpense(ObjectId userId) {
        return calculateTotal(userId, "EXPENSE");
    }

    private double calculateTotal(ObjectId userId, String type) {
        double total = 0;
        List<Transaction> transactions = transactionCollection.find(
                and(eq("user_id", userId), eq("type", type))).into(new ArrayList<>());

        for (Transaction t : transactions) {
            total += t.getAmount();
        }
        return total;
    }
}
