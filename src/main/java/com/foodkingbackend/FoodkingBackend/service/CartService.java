package com.foodkingbackend.FoodkingBackend.service;

import com.foodkingbackend.FoodkingBackend.entity.Cart;
import com.foodkingbackend.FoodkingBackend.entity.CartItem;
import com.foodkingbackend.FoodkingBackend.entity.FoodItem;
import com.foodkingbackend.FoodkingBackend.entity.User;
import com.foodkingbackend.FoodkingBackend.repository.CartItemRepository;
import com.foodkingbackend.FoodkingBackend.repository.CartRepository;
import com.foodkingbackend.FoodkingBackend.repository.FoodItemRepository;
import com.foodkingbackend.FoodkingBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodItemRepository foodItemRepository;
    @Autowired
    CartItemRepository cartItemRepository;


    /**
     * Adds a specified quantity of a food item to the user's cart.
     *
     * This method performs the following steps:
     * - Validates that the user and the food item exist in the database.
     * - Checks if the user already has a cart; if not, a new cart is created.
     * - If the item already exists in the cart, its quantity is updated to the provided value.
     * - If the item does not exist in the cart, it is added as a new CartItem.
     * - Recalculates the total amount in the cart based on all items and their quantities.
     * - Saves and returns the updated cart.
     *
     * The method is transactional to ensure atomicity of operations involving multiple entities.
     *
     * @param userId     The ID of the user adding the item to the cart.
     * @param foodItemId The ID of the food item being added.
     * @param qty        The quantity of the food item to add or update in the cart.
     * @return The updated Cart object after the item has been added or updated.
     * @throws RuntimeException if the user or food item is not found, or any other error occurs during the process.
     */
    @Transactional
    public Cart addToCart(Long userId, Long foodItemId, int qty) {
        try {
//        check if user is present
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
//        check if foodItem is present
            FoodItem foodItem = foodItemRepository.findById(foodItemId)
                    .orElseThrow(() -> new RuntimeException("Food Item Not Found"));

//        check if cart exists
            Cart cart = cartRepository.findByUserId(userId).orElse(null);

//        if not, create new cart
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
                cart.setItems(new ArrayList<>());
            }

//        check if item already is cart
            Optional<CartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getFoodItem().getId().equals(foodItemId)).findFirst();

            if (existingItem.isPresent()) {
    //            update quantity
                existingItem.get().setQuantity(qty);
            } else {
    //          add new item to cart
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setFoodItem(foodItem);
                newItem.setQuantity(qty);
                cart.getItems().add(newItem);
            }

            double total = cart.getItems().stream()
                    .mapToDouble(item -> item.getFoodItem().getPrice() * item.getQuantity())
                    .sum();
            cart.setTotalAmount(total);

            return cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the cart associated with the given user ID.
     *
     * This method fetches the cart for the specified user from the repository.
     * If no cart exists for the user, a RuntimeException is thrown.
     *
     * @param userId The ID of the user whose cart is to be retrieved.
     * @return The Cart object associated with the user.
     * @throws RuntimeException if no cart is found for the user.
     */
    public Cart getCartForUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
    }


    /**
     * Removes a specific food item from a user's cart and updates the cart total.
     *
     * This method:
     * - Retrieves the user's cart.
     * - Removes the item with the specified item ID from the cart.
     * - Recalculates the total cart amount based on remaining items.
     * - Saves the updated cart back to the repository.
     *
     * If the cart does not exist, a RuntimeException is thrown.
     *
     * @param userId The ID of the user whose cart is being modified.
     * @param itemId The ID of the food item to remove from the cart.
     * @throws RuntimeException if the user's cart is not found.
     */
    public void removeFromUserCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getFoodItem().getId().equals(itemId));


        // update the total
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getFoodItem().getPrice() * item.getFoodItem().getQuantity())
                .sum();
        cart.setTotalAmount(total);

        cartRepository.save(cart);
    }
}
