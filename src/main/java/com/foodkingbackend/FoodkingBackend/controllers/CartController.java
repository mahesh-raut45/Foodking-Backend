package com.foodkingbackend.FoodkingBackend.controllers;

import com.foodkingbackend.FoodkingBackend.entity.Cart;
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

    /**
     * Adds a specified quantity of a food item to the user's cart.
     *
     * This method delegates to the CartService to add the given food item to the cart
     * associated with the specified user. If the user does not have an existing cart,
     * one may be created. The updated cart is then returned in the response.
     *
     * @param userId      The ID of the user who is adding the item to their cart.
     * @param foodItemId  The ID of the food item to be added.
     * @param qty         The quantity of the food item to add.
     * @return A ResponseEntity containing the updated Cart object.
     */
    @PostMapping("/{userId}/add/{foodItemId}/{qty}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId, @PathVariable Long foodItemId, @PathVariable int qty) {
        Cart cart = cartService.addToCart(userId, foodItemId, qty);
        return ResponseEntity.ok(cart);
    }


    /**
     * Retrieves the cart associated with a specific user.
     *
     * This method fetches the current cart for the given user ID by delegating to the CartService.
     * If the user has no cart or the cart is empty, the returned Cart object will reflect that.
     *
     * @param userId The ID of the user whose cart is to be retrieved.
     * @return A ResponseEntity containing the user's Cart object.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }


    /**
     * Removes a specific item from a user's cart.
     *
     * This method removes the food item with the given item ID from the cart of the specified user.
     * If the item does not exist in the cart or if the user/cart is invalid, the service layer is expected
     * to handle and throw appropriate exceptions.
     *
     * @param userId The ID of the user whose cart item is to be removed.
     * @param itemId The ID of the item to be removed from the user's cart.
     * @return A ResponseEntity with a success message upon successful removal.
     */
    @DeleteMapping("/user/{userId}/item/{itemId}")
    public ResponseEntity<?> removeFromUserCart(@PathVariable Long userId, @PathVariable Long itemId) {
        cartService.removeFromUserCart(userId, itemId);
        return ResponseEntity.ok("Item removed from cart");
    }

}
