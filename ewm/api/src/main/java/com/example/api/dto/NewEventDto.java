package com.example.api.dto;

import com.example.api.Constant;
import com.example.api.dto.constraint.EventDateValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class NewEventDto {
    @Size(min = 20)
    @Size(max = 2000)
    @NotBlank
    private String annotation;

    @NotNull
    private Long category;

    @Size(min = 20)
    @Size(max = 2000)
    @NotBlank
    private String description;

    @EventDateValidator
    @JsonFormat(pattern = Constant.TIME_PATTERN)
    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    @Valid
    private LocationDto location;
    private Boolean paid = false;

    @PositiveOrZero
    private Long participantLimit = 0L;
    private Boolean requestModeration = true;

    @Size(min = 3)
    @Size(max = 120)
    @NotBlank
    private String title;
}
