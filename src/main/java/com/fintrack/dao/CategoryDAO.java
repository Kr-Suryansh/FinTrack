package com.fintrack.dao;

import com.fintrack.model.Category;
import com.fintrack.util.DatabaseManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CategoryDAO {
    private final MongoCollection<Category> categoryCollection;

    public CategoryDAO() {
        MongoDatabase database = DatabaseManager.getInstance().getDatabase();
        this.categoryCollection = database.getCollection("categories", Category.class);
        initDefaults();
    }

    private void initDefaults() {
        if (categoryCollection.countDocuments() == 0) {
            List<Category> defaults = new ArrayList<>();
            // Income
            defaults.add(new Category("Salary", "INCOME", "fas-money-bill-wave"));
            defaults.add(new Category("Freelance", "INCOME", "fas-laptop-code"));
            defaults.add(new Category("Investments", "INCOME", "fas-chart-line"));
            // Expense
            defaults.add(new Category("Food", "EXPENSE", "fas-utensils"));
            defaults.add(new Category("Rent", "EXPENSE", "fas-home"));
            defaults.add(new Category("Transport", "EXPENSE", "fas-bus"));
            defaults.add(new Category("Entertainment", "EXPENSE", "fas-film"));
            defaults.add(new Category("Health", "EXPENSE", "fas-medkit"));
            defaults.add(new Category("Shopping", "EXPENSE", "fas-shopping-bag"));

            categoryCollection.insertMany(defaults);
        }
    }

    public List<Category> findAll() {
        // Return system defaults (userId == null)
        return categoryCollection.find(eq("user_id", null)).into(new ArrayList<>());
    }

    public List<Category> findAll(ObjectId userId) {
        // Return defaults + user specific
        List<Category> all = new ArrayList<>();
        all.addAll(categoryCollection.find(eq("user_id", null)).into(new ArrayList<>()));
        if (userId != null) {
            all.addAll(categoryCollection.find(eq("user_id", userId)).into(new ArrayList<>()));
        }
        return all;
    }

    public void save(Category category) {
        categoryCollection.insertOne(category);
    }

    public void update(Category category) {
        categoryCollection.replaceOne(eq("_id", category.getId()), category);
    }

    public Category findByName(String name) {
        return categoryCollection.find(eq("name", name)).first();
    }
}
