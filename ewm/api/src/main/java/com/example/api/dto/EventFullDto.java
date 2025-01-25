package com.example.api.dto;

import com.example.api.Constant;
import com.example.api.dto.enums.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EventFullDto extends EventDtoWithViews {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;

    @JsonFormat(pattern = Constant.TIME_PATTERN)
    private LocalDateTime createdOn;
    private String description;

    @JsonFormat(pattern = Constant.TIME_PATTERN)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Long participantLimit = 0L;

    @JsonFormat(pattern = Constant.TIME_PATTERN)
    private LocalDateTime publishedOn;
    private Boolean requestModeration = true;
    private EventStatus state;
    private String title;
}
