package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.util.validation.EventDateValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static ru.practicum.ewm.util.Constant.TIME_PATTERN;

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
