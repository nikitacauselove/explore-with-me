package com.example.main.dto;

import com.example.main.util.validation.EventDateValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.example.main.util.Constant.TIME_PATTERN;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class NewEventDto {
    @Length(min = 20, max = 2000)
    @NotBlank
    private String annotation;

    @NotNull
    private Long category;

    @Length(min = 20, max = 7000)
    @NotBlank
    private String description;

    @EventDateValidator
    @JsonFormat(pattern = TIME_PATTERN)
    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    @Valid
    private LocationDto location;
    private Boolean paid = false;

    @PositiveOrZero
    private Long participantLimit = 0L;
    private Boolean requestModeration = true;

    @Length(min = 3, max = 120)
    @NotBlank
    private String title;
}
