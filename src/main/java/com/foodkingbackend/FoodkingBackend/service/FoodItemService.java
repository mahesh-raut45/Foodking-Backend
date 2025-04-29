package com.foodkingbackend.FoodkingBackend.service;

import com.foodkingbackend.FoodkingBackend.entity.FoodItem;
import com.foodkingbackend.FoodkingBackend.repository.FoodItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    @PostConstruct
    public void updateExistingFoodItems() {
        foodItemRepository.updateQuantityForExistingItems();
    }

    public FoodItemService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    /**
     * Saves a list of food items, either updating existing ones or adding new ones.
     *
     * This method performs a bulk insert for a list of `FoodItem` objects. For each food item:
     * - If the item has an ID and already exists in the repository, it is updated with the new values.
     * - If the item is new (does not exist in the repository), it is saved as a new entry.
     *
     * The method ensures the latest data is fetched and updated, and initialization of related entities
     * (like `mealType` and `tags`) is triggered before saving the updated food item. If an error occurs while
     * saving an item, it is logged and the process continues for the remaining items.
     *
     * @param foodItems The list of `FoodItem` objects to be saved or updated.
     * @return A list of `FoodItem` objects that have been saved (either new or updated).
     */
    public List<FoodItem> saveAllFoodItems(List<FoodItem> foodItems) {
        List<FoodItem> savedItems = new ArrayList<>();

        for (FoodItem item : foodItems) {
            try {
                if (item.getId() != null && foodItemRepository.existsById(item.getId())) {
                    // Fetch the latest version from DB
                    FoodItem existingItem = foodItemRepository.findById(item.getId()).orElseThrow();
                    existingItem.getMealType().size(); // Force initialization
                    existingItem.getTags().size();     // Force initialization

                    // Update fields manually
                    existingItem.setName(item.getName());
                    existingItem.setPrice(item.getPrice());
                    existingItem.setQuantity(item.getQuantity());
                    existingItem.setImage(item.getImage());
                    existingItem.setCaloriesPerServing(item.getCaloriesPerServing());
                    existingItem.setCuisine(item.getCuisine());
                    existingItem.setRating(item.getRating());
                    existingItem.setReviewCount(item.getReviewCount());
                    existingItem.setMealType(item.getMealType());
                    existingItem.setTags(item.getTags());

                    savedItems.add(foodItemRepository.save(existingItem));
                } else {
                    savedItems.add(foodItemRepository.save(item)); // New item
                }
            } catch (Exception e) {
                log.error("Error saving item with ID: {}", item.getId(), e);
            }
        }

        return savedItems;
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
