package com.foodkingbackend.FoodkingBackend.repository;

import com.foodkingbackend.FoodkingBackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {
}
