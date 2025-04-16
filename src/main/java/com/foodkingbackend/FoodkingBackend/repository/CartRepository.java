package com.foodkingbackend.FoodkingBackend.repository;

import com.foodkingbackend.FoodkingBackend.entity.Cart;
import com.foodkingbackend.FoodkingBackend.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}
