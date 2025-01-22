package ru.practicum.ewm.category.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {
    private final Long id;

    @Length(min = 1, max = 50)
    @NotBlank
    private final String name;
}
