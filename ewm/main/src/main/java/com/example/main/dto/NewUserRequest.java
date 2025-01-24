package com.example.main.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class NewUserRequest {
    @Length(min = 2, max = 250)
    @NotBlank
    private String name;

    @Length(min = 6, max = 254)
    @Email
    @NotBlank
    private String email;
}
