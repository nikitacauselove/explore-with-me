package com.example.main.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryDto {
    private final Long id;

    @Length(min = 1, max = 50)
    @NotBlank
    private final String name;
}
