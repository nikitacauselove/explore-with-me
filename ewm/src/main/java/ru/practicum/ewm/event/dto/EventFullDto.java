package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.event.model.EventStatus;

import java.time.LocalDateTime;

import static ru.practicum.ewm.util.Constant.TIME_PATTERN;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EventFullDto extends EventDtoWithViews {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;

    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime createdOn;
    private String description;

    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Long participantLimit = 0L;

    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime publishedOn;
    private Boolean requestModeration = true;
    private EventStatus state;
    private String title;
}
