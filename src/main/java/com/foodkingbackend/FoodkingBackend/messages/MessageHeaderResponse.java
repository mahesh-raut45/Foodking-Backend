package com.foodkingbackend.FoodkingBackend.messages;

import lombok.Data;

//
@Data
public class MessageHeaderResponse {
    private int errorCode = 0;
    private String errorString = "";
}
