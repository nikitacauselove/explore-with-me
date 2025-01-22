package ru.practicum.ewm.request.dto;

import lombok.Data;
import ru.practicum.ewm.request.model.RequestStatus;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
    private final Long id;
    private final LocalDateTime created;
    private final Long event;
    private final Long requester;
    private final RequestStatus status;
}
