package ru.practicum.ewm.request.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
