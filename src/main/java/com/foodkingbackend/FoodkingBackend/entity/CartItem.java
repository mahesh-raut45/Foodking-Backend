package com.foodkingbackend.FoodkingBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FoodItem foodItem;

    private int quantity = 0;   // Initially setting to 0.

    @ManyToOne
    @JsonBackReference // this will break the infinite loop for JSON serialized. This will stop cart from being serialized inside CartItem.
    private Cart cart;
}
