package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
