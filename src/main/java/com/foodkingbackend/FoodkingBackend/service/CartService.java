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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodItemRepository foodItemRepository;
    @Autowired
    CartItemRepository cartItemRepository;


    @Transactional
    public Cart addToCart(Long userId, Long foodItemId, int qty) {
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
            existingItem.get().setQuantity(existingItem.get().getQuantity() + qty);
//            foodItem.setQuantity(qty);
//            existingItem.get().getFoodItem().setQuantity(foodItem.getQuantity() + qty);
        } else {
//          add new item to cart
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setFoodItem(foodItem);
//            foodItem.setQuantity(foodItem.getQuantity() + qty); // I was changing the original item's qty
            newItem.setQuantity(qty);
            cart.getItems().add(newItem);
        }
//        update the total
//        double total = cart.getItems().stream()
//                .mapToDouble(item -> item.getFoodItem().getPrice() * item.getFoodItem().getQuantity())
//                .sum();
//        cart.setTotalAmount(total);

        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getFoodItem().getPrice() * item.getQuantity())
                .sum();
        cart.setTotalAmount(total);

        return cartRepository.save(cart);
    }

    public Cart getCartForUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
    }

    public void removeFromUserCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

//        before removing that item, change the cartItem qty;
//        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        cart.getItems().removeIf(item -> item.getFoodItem().getId().equals(itemId));


        //        update the total
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getFoodItem().getPrice() * item.getFoodItem().getQuantity())
                .sum();
        cart.setTotalAmount(total);

        cartRepository.save(cart);
    }
}
