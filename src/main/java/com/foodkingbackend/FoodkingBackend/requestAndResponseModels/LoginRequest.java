package com.foodkingbackend.FoodkingBackend.requestAndResponseModels;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

}
