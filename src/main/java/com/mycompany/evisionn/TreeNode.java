
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.evisionn;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author hanee
 */

class TreeNode {
    String value;
    List<TreeNode> children;

    public TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
  // Find existing child by value, or return null if not found
        private TreeNode getChild(String name) {
        for (TreeNode child : children) {
            if (child.value.equals(name)) {
                return child;
            }
        }
        return null;
    }
        public void insertMultiple(List<String> keys, List<String> paths) {
    for (int i = 0; i < keys.size(); i++) {
        String key = keys.get(i);
        String path = paths.get(i);
        insert(paths, keys);  // reuse your existing insert method
    }
}

    // Insert path and key
    public void insert(List<String> path, List<String> keys) {
            //String[] parts = path.split("/");
            TreeNode current = this;
             for (String part : path) {
        TreeNode child = current.getChild(part);
        if (child == null) {
            child = new TreeNode(part);
            current.children.add(child);
        }
        current = child;
    }
            for (String key : keys) {
        current.children.add(new TreeNode(key));
    }        }
    
    public void print(String indent) {
    if (value != null && !value.equals("")) {
        System.out.println(indent + value);
    }
    for (TreeNode child : children) {
        child.print(indent + "  ");
    }
}
    public void insertFromList(List<KeyPath> entries) {
        for (KeyPath entry : entries) {
            insert(entry.pathParts, Arrays.asList(entry.key));
        }
    }

    public static class KeyPath {
    public String key;
    public List<String> pathParts;

    public KeyPath(String key, List<String> pathParts) {
        this.key = key;
        this.pathParts = pathParts;
    }
}




   public static void main(String[] args) {
    List<KeyPath> entries = new ArrayList<>();
    entries.add(new KeyPath("f", Arrays.asList("a", "b", "o", "e")));
    entries.add(new KeyPath("z", Arrays.asList("a", "b", "p")));
    entries.add(new KeyPath("x", Arrays.asList("a", "q")));

    TreeNode root = new TreeNode("");
    root.insertFromList(entries);
    root.print("");
}
}

