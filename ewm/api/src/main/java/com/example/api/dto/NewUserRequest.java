package com.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class NewUserRequest {
    @Size(min = 2)
    @Size(max = 250)
    @NotBlank
    private String name;

    @Size(min = 6)
    @Size(max = 254)
    @Email
    @NotBlank
    private String email;
}
