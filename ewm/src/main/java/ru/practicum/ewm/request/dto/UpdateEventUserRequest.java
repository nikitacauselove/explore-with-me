package ru.practicum.ewm.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.event.dto.LocationDto;
import ru.practicum.ewm.request.model.UserStateAction;
import ru.practicum.ewm.util.validation.EventDateValidator;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static ru.practicum.ewm.util.Constant.TIME_PATTERN;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UpdateEventUserRequest {
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
    private UserStateAction stateAction;
}
