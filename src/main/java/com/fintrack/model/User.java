package com.fintrack.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.util.Date;

public class User {
    @BsonId
    private ObjectId id;

    @BsonProperty("username")
    private String username;

    @BsonProperty("password")
    private String password;

    @BsonProperty("created_at")
    private Date createdAt;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = new Date();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
