package com.foodkingbackend.FoodkingBackend.controllers;

import com.foodkingbackend.FoodkingBackend.entity.Cart;
import com.foodkingbackend.FoodkingBackend.requestAndResponseModels.AddToCartRequest;
import com.foodkingbackend.FoodkingBackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/add/{foodItemId}/{qty}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId, @PathVariable Long foodItemId, @PathVariable int qty) {
        Cart cart = cartService.addToCart(userId, foodItemId, qty);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }

    @DeleteMapping("/user/{userId}/item/{itemId}")
    public ResponseEntity<?> removeFromUserCart(@PathVariable Long userId, @PathVariable Long itemId) {
        cartService.removeFromUserCart(userId, itemId);
        return ResponseEntity.ok("Item removed from cart");
    }

}
