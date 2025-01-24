package com.example.main.dto;

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
public class NewCategoryDto {
    @Length(min = 1, max = 50)
    @NotBlank
    private String name;
}
