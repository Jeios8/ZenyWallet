package com.ZenyWallet.model;

import java.sql.*;
// import java.util.HashMap;

public class UserRepository {
    private final String db_url;
    // private final String db_username;
    // private final String db_password;

    public UserRepository(String db_url) {
        this.db_url = db_url;
        // this.db_username = db_username;
        // this.db_password = db_password;
    }

    public boolean authenticateUserID(String userID) {
        String query = "SELECT COUNT(*) FROM USERS WHERE userid = ?";

        try (Connection conn = DriverManager.getConnection(db_url);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if userid exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean authenticatePIN(String userID, String pin) {
        String query = "SELECT COUNT(*) FROM USERS WHERE userid = ? AND pin = ?";

        try (Connection conn = DriverManager.getConnection(db_url);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userID);
            stmt.setString(2, pin);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the pin matches for the userid
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addUser(User user) {
        String checkQuery = "SELECT COUNT(*) FROM USERS WHERE userid = ?";
        String insertQuery = "INSERT INTO USERS (userid, pin, name, balance) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(db_url)) {
            // Check if the user already exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, user.getUserID());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("User with ID " + user.getUserID() + " already exists.");
                    return; // User already exists
                }
            }

            // Add new user
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, user.getUserID());
                insertStmt.setString(2, user.getPin());
                insertStmt.setString(3, user.getName());
                insertStmt.setDouble(4, user.getBalance());
                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User " + user.getUserID() + " added successfully.");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public User getUser(String userID) {
        String query = "SELECT pin, name, balance FROM USERS WHERE userid = ?";
        User user = null;

        try (Connection conn = DriverManager.getConnection(db_url);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String pin = rs.getString("pin");
                    String name = rs.getString("name");
                    double balance = rs.getDouble("balance");

                    // Create a User object
                    user = new User(userID, pin, name, balance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void updateBalance(String userID, double newBalance) {
        String query = "UPDATE USERS SET balance = ? WHERE userid = ?";

        try (Connection conn = DriverManager.getConnection(db_url);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameters
            stmt.setDouble(1, newBalance); // New balance
            stmt.setString(2, userID);    // User ID

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated <= 0) {
                System.out.println("User ID not found: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
