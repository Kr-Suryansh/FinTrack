package com.fintrack.dao;

import com.fintrack.model.User;
import com.fintrack.util.DatabaseManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

public class UserDAO {
    private final MongoCollection<User> userCollection;

    public UserDAO() {
        MongoDatabase database = DatabaseManager.getInstance().getDatabase();
        this.userCollection = database.getCollection("users", User.class);
    }

    public void save(User user) {
        userCollection.insertOne(user);
    }

    public User findByUsername(String username) {
        return userCollection.find(eq("username", username)).first();
    }

    public boolean exists(String username) {
        return findByUsername(username) != null;
    }
}
