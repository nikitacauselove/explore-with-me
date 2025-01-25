package com.example.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
    private final Long id;

    @Size(min = 1)
    @Size(max = 50)
    @NotBlank
    private final String name;
}
