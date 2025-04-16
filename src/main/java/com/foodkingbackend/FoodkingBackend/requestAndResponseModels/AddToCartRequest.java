package com.foodkingbackend.FoodkingBackend.requestAndResponseModels;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private Long userId;
    private Long foodItemId;
    private int quantity;
}
