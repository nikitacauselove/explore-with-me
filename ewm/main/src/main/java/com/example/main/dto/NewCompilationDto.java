package com.example.main.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned = false;

    @Length(min = 1, max = 50)
    @NotBlank
    private String title;
}
