package com.fintrack.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.util.Date;

public class Transaction {
    @BsonId
    private ObjectId id;

    @BsonProperty("user_id")
    private ObjectId userId;

    @BsonProperty("type")
    private String type; // "INCOME" or "EXPENSE"

    @BsonProperty("amount")
    private double amount;

    @BsonProperty("category")
    private String category;

    @BsonProperty("description")
    private String description;

    @BsonProperty("date")
    private Date date;

    public Transaction() {
    }

    public Transaction(ObjectId userId, String type, double amount, String category, String description, Date date) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
