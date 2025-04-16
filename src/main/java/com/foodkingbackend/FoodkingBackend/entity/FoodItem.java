package com.foodkingbackend.FoodkingBackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "food_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity = 1;

    @Column(name = "image")
    private String image;

    private int caloriesPerServing;
    private String cuisine;
    private double rating;
    private int reviewCount;

    @ElementCollection
    @CollectionTable(name = "meal_types", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "mealType")
    private List<String> mealType;

    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "tag")
    private List<String> tags;
}
