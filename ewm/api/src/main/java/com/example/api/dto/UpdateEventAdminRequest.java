package com.example.api.dto;

import com.example.api.Constant;
import com.example.api.dto.constraint.EventDateValidator;
import com.example.api.dto.enums.AdminStateAction;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
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
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    @EventDateValidator
    @JsonFormat(pattern = Constant.TIME_PATTERN)
    private LocalDateTime eventDate;

    @Valid
    private LocationDto location;
    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;
    private Boolean requestModeration;

    @Size(min = 3, max = 120)
    private String title;
    private AdminStateAction stateAction;
}
