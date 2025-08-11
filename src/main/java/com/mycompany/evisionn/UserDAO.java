/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.evisionn;

/**
 *
 * @author zeynatghallab
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class KeyPath {
    String key;
    String fieldPath;

    KeyPath(String key, String fieldPath) {
        this.key = key;
        this.fieldPath = fieldPath;
    }
}

public class UserDAO {
    // Database connection info
    private static final String URL = "jdbc:postgresql://localhost:5432/db1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2003";

    public UserDAO() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL Driver not found!");
            e.printStackTrace();
        }
    }

    // Add entry (insert if not exists, update if exists)
    public void addKeyPath(String key, String fieldPath) {
        String checkSql = "SELECT COUNT(*) FROM table_new WHERE \"key\" = ?";
        String insertSql = "INSERT INTO table_new (\"key\", field_path) VALUES (?, ?)";
        String updateSql = "UPDATE table_new SET field_path = ? WHERE \"key\" = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            boolean exists = false;

            // Check if key exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, key);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        exists = true;
                    }
                }
            }

            if (exists) {
                // Update if exists
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, fieldPath);
                    updateStmt.setString(2, key);
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert if new
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, key);
                    insertStmt.setString(2, fieldPath);
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error adding/updating Key-Path: " + key);
            e.printStackTrace();
        }
    }

    // Get all entries as separate key/fieldPath values
    public List<KeyPath> getAllKeyPaths() {
        List<KeyPath> keyPaths = new ArrayList<>();
        String sql = "SELECT \"key\", field_path FROM table_new ORDER BY \"key\" ASC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String key = rs.getString("key");
                String fieldPath = rs.getString("field_path");
                keyPaths.add(new KeyPath(key, fieldPath));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error reading Key-Paths");
            e.printStackTrace();
        }
        return keyPaths;
    }

    // Main method for testing
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // Add or update entries
        dao.addKeyPath("C", "a/b");
        dao.addKeyPath("F", "a/b/d/e");
        dao.addKeyPath("X", "q/u");
        dao.addKeyPath("Y", "q/p");
        dao.addKeyPath("G", "a/b");

        // Output all entries with separate key and field path
        System.out.println("\n📋 All Entries in Database:");
        for (KeyPath kp : dao.getAllKeyPaths()) {
            System.out.println("Key: " + kp.key);
            System.out.println("Field Path: " + kp.fieldPath);
            System.out.println("-------------------");
        }
    }
}

