package com.example.main.dto;

import com.example.main.repository.model.AdminStateAction;
import com.example.main.util.validation.EventDateValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
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
public class UpdateEventAdminRequest {
    @Length(min = 20, max = 2000)
    private String annotation;
    private Long category;

    @Length(min = 20, max = 7000)
    private String description;

    @EventDateValidator
    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime eventDate;

    @Valid
    private LocationDto location;
    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;
    private Boolean requestModeration;

    @Length(min = 3, max = 120)
    private String title;
    private AdminStateAction stateAction;
}
