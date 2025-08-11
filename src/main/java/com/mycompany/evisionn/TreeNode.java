
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.evisionn;
import java.util.*;
/**
 *
 * @author hanee
 */
import java.util.*;

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
    // Insert path and key
    public void insert(String key, String path) {
            String[] parts = path.split("/");
            TreeNode current = this;
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                TreeNode child = current.getChild(part);
                if (child == null) {
                    child = new TreeNode(part);
                    current.children.add(child);
                }
                current = child;
            }
            current.children.add(new TreeNode(key));
        }
    
    public void print(String indent) {
    if (value != null && !value.equals("")) {
        System.out.println(indent + value);
    }
    for (TreeNode child : children) {
        child.print(indent + "  ");
    }
}



    public static void main(String[] args) {
        TreeNode root = new TreeNode("");

        root.insert("a/b", "c");     // Creates top-level "a" tree
        root.insert("a/b/d/e", "f");     // Goes into existing "a" tree
        root.insert("q/u/", "x");   // Creates new top-level "m" tree
        root.insert("q/r/", "v");
        root.insert("c/t/", "v");
         root.insert("a/b", "l");
        
        root.print("");
    }
}