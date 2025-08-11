package com.mycompany.evisionn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.mycompany.evisionn.TreeNode;

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
    private static final String PASSWORD = "26233254";

    // Store entries in memory
    private List<KeyPath> keyPathCache = new ArrayList<>();

    public UserDAO() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL Driver not found!");
            e.printStackTrace();
        }
        refreshCache();
    }

    // Refresh in-memory list from DB
    private void refreshCache() {
        keyPathCache = getAllKeyPathsFromDB();
    }

    // Add entry (insert if not exists, update if exists)
    public void addKeyPath(String key, String fieldPath) {
        String checkSql = "SELECT COUNT(*) FROM table_new WHERE \"key\" = ?";
        String insertSql = "INSERT INTO table_new (\"key\", field_path) VALUES (?, ?)";
        String updateSql = "UPDATE table_new SET field_path = ? WHERE \"key\" = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            boolean exists = false;

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, key);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        exists = true;
                    }
                }
            }

            if (exists) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, fieldPath);
                    updateStmt.setString(2, key);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, key);
                    insertStmt.setString(2, fieldPath);
                    insertStmt.executeUpdate();
                }
            }

            // Refresh in-memory list after change
            refreshCache();

        } catch (SQLException e) {
            System.err.println("❌ Error adding/updating Key-Path: " + key);
            e.printStackTrace();
        }
    }

    // Internal: Get all entries from DB
    private List<KeyPath> getAllKeyPathsFromDB() {
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

    public List<KeyPath> getAllKeyPaths() {
        return keyPathCache;
    }

<<<<<<< HEAD
   public void getTree(List<KeyPath> entries) {
    System.out.println("🌳 Building tree from " + entries.size() + " entries...");
    
    // Convert entries with string paths to tree entries with list paths
    List<TreeNode.KeyPath> treeEntries = getKeyPathsForTree();
    
    TreeNode root = new TreeNode("");
    root.insertFromList(treeEntries);
    root.print("");
}

    public List<TreeNode.KeyPath> getKeyPathsForTree() {
    List<TreeNode.KeyPath> treeEntries = new ArrayList<>();
    for (KeyPath kp : keyPathCache) {
        List<String> parts = Arrays.asList(kp.fieldPath.split("/"));
        treeEntries.add(new TreeNode.KeyPath(kp.key, parts));
=======
    // Tree node structure
    static class TreeNode {
        String name;
        List<TreeNode> children = new ArrayList<>();

        TreeNode(String name) {
            this.name = name;
        }

        TreeNode getOrCreateChild(String name) {
            for (TreeNode child : children) {
                if (child.name.equals(name)) {
                    return child;
                }
            }
            TreeNode newChild = new TreeNode(name);
            children.add(newChild);
            return newChild;
        }
    }

    // Build and print the tree
    public void getTree(List<KeyPath> entries) {
        TreeNode root = new TreeNode("ROOT");

        for (KeyPath kp : entries) {
            String[] parts = kp.fieldPath.split("/");
            TreeNode current = root;

            // Traverse or create nodes for the path
            for (String part : parts) {
                current = current.getOrCreateChild(part);
            }

            // Add the key as the leaf node
            current.getOrCreateChild(kp.key);
        }

        // Print the tree structure
        printTree(root, 0);
    }

    // Recursive tree printer
    private void printTree(TreeNode node, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "- " + node.name);
        for (TreeNode child : node.children) {
            printTree(child, depth + 1);
        }
>>>>>>> c2fa72964f64691c95774c3b4b0d6d046703d215
    }
    return treeEntries;
}


    // Main method for testing
    public static void main(String[] args) {
     
    UserDAO dao = new UserDAO();

    dao.addKeyPath("C", "a/b");
    dao.addKeyPath("F", "a/b/d/e");
    dao.addKeyPath("X", "q/u");
    dao.addKeyPath("Y", "q/p");
    dao.addKeyPath("G", "a/b");

    // Get all KeyPath entries from the DB (with string paths)
    List<KeyPath> entries = dao.getAllKeyPaths();

<<<<<<< HEAD
    // Convert to TreeNode.KeyPath (with List<String> pathParts)
    List<TreeNode.KeyPath> treeEntries = new ArrayList<>();
    for (KeyPath kp : entries) {
        List<String> parts = Arrays.asList(kp.fieldPath.split("/"));
        treeEntries.add(new TreeNode.KeyPath(kp.key, parts));
=======
        System.out.println("\n🌳 Tree Structure:");
        dao.getTree(dao.getAllKeyPaths());
>>>>>>> c2fa72964f64691c95774c3b4b0d6d046703d215
    }

    // Create a TreeNode root
    TreeNode root = new TreeNode("");

    // Call insertFromList with converted entries
    root.insertFromList(treeEntries);

    // Print the constructed tree
    root.print("");
}

}
