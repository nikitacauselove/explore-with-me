package com.example.api.dto;

import com.example.api.dto.enums.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
    private final Long id;
    private final LocalDateTime created;
    private final Long event;
    private final Long requester;
    private final RequestStatus status;
}
