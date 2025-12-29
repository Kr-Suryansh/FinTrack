package com.fintrack.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fintrack.dao.UserDAO;
import com.fintrack.model.User;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean register(String username, String password) {
        if (userDAO.exists(username)) {
            return false; // User already exists
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User newUser = new User(username, hashedPassword);
        userDAO.save(newUser);
        return true;
    }

    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null; // User not found
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (result.verified) {
            return user;
        }
        return null; // Invalid password
    }
}
