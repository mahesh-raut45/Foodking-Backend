package com.foodkingbackend.FoodkingBackend.service;

import com.foodkingbackend.FoodkingBackend.entity.FoodItem;
import com.foodkingbackend.FoodkingBackend.repository.FoodItemRepository;
import jakarta.annotation.PostConstruct;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    @PostConstruct
    public void updateExistingFoodItems() {
        foodItemRepository.updateQuantityForExistingItems();
    }

    public FoodItemService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    //    Bulk insert
    public List<FoodItem> saveAllFoodItems(List<FoodItem> foodItems) {
        try {
            return foodItemRepository.saveAll(foodItems);
        } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException e) {
            // Log the error (optional)
            System.err.println("Optimistic locking error while saving food items: " + e.getMessage());

            // You can return only new items (without IDs), or just skip failed ones
            List<FoodItem> newItems = foodItems.stream()
                    .filter(item -> item.getId() == null)
                    .collect(Collectors.toList());

            // Try saving only the new ones
            return foodItemRepository.saveAll(newItems);
        } catch (Exception e) {
            // Generic catch for other issues
            System.err.println("General error: " + e.getMessage());
            throw e; // rethrow or return empty list if you prefer
        }
    }
//
//    //    update an existing food item
//    public FoodItem updateFoodItem(Long id, FoodItem updatedFoodItem) {
//        return foodItemRepository.findById(id)
//                .map(existingItem -> {
//                    existingItem.setName(updatedFoodItem.getName());
//                    existingItem.setPrice(updatedFoodItem.getPrice());
//                    existingItem.setImageUrl(updatedFoodItem.getImageUrl());
//                    existingItem.setCaloriesPerServing(updatedFoodItem.getCaloriesPerServing());
//                    existingItem.setCuisine(updatedFoodItem.getCuisine());
//                    existingItem.setMealType(updatedFoodItem.getMealType());
//                    existingItem.setRating(updatedFoodItem.getRating());
//                    existingItem.setReviewCount(updatedFoodItem.getReviewCount());
//                    existingItem.setTags(updatedFoodItem.getTags());
//                    return foodItemRepository.save(existingItem);
//                }).orElseThrow(() -> new RuntimeException("Food item not found"));
//    }
//
    public void addFoodItem(FoodItem foodItem) {
        foodItemRepository.save(foodItem);
    }


    public List<FoodItem> getAllFoodItems() {
       return foodItemRepository.findAll();
    }

    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id).orElse(new FoodItem());
    }
}
