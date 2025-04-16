package com.foodkingbackend.FoodkingBackend.requestAndResponseModels;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private Long id;
    private String userName;
    private String email;
    private String text;

    public JwtAuthResponse(  String text, Long id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.text = text;
    }
}
