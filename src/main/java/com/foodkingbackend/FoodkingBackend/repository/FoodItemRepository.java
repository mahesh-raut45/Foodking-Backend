package com.foodkingbackend.FoodkingBackend.repository;

import com.foodkingbackend.FoodkingBackend.entity.FoodItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE FoodItem f SET f.quantity = 1 WHERE f.quantity = 0")
    void updateQuantityForExistingItems();


}
