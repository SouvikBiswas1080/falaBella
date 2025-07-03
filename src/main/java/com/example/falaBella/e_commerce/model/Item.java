package com.example.falaBella.e_commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    @Id
    private Long itemId;
    private String name;
    private int totalQuantity;
    private int reservedQuantity;
}