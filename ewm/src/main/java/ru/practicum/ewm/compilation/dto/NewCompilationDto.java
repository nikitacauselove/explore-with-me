package ru.practicum.ewm.compilation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned = false;

    @Length(min = 1, max = 50)
    @NotBlank
    private String title;
}
