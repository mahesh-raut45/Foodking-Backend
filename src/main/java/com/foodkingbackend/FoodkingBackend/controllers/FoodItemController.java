package com.foodkingbackend.FoodkingBackend.controllers;


import com.foodkingbackend.FoodkingBackend.entity.FoodItem;
import com.foodkingbackend.FoodkingBackend.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/food")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @PostMapping("/foodItem")
    public void addFoodItem(@RequestBody FoodItem foodItem) {
        foodItemService.addFoodItem(foodItem);
    }

    @PostMapping("/bulk-insert")
    public List<FoodItem> insertBulkFoodItems(@RequestBody List<FoodItem> foodItems) {
        return foodItemService.saveAllFoodItems(foodItems);
    }
//
//    //    update food item
//    @PutMapping("/update/{id}")
//    public FoodItem updateFoodItem(@PathVariable Long id, @RequestBody FoodItem foodItem) {
//        return foodItemService.updateFoodItem(id, foodItem);
//    }


    @GetMapping("/getAll")
    public List<FoodItem> getAllFoodItems() {
        return foodItemService.getAllFoodItems();
    }

    @GetMapping("/id/{id}")
    public FoodItem getFoodItemById(@PathVariable("id") Long id) {
        return foodItemService.getFoodItemById(id);
    }

}
