package com.fintrack.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Category {
    @BsonId
    private ObjectId id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("user_id")
    private ObjectId userId;

    @BsonProperty("budget_limit")
    private double budgetLimit;

    @BsonProperty("type")
    private String type; // "INCOME" or "EXPENSE"

    @BsonProperty("icon")
    private String icon;

    public Category() {
    }

    public Category(String name, String type, String icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }
}
