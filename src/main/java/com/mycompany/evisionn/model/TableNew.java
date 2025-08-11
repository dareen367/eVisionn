/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.evisionn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author hanee
 */
@Entity
@Table(name = "table_new")
public class TableNew {
//    int id;
    
    @Column(name = "key")
    String key;
    @Column
    String fieldPath;
}
