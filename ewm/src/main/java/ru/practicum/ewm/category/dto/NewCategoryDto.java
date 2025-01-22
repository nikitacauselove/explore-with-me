package ru.practicum.ewm.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class NewCategoryDto {
    @Length(min = 1, max = 50)
    @NotBlank
    private String name;
}
