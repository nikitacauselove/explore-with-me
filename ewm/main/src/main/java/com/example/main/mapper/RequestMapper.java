package com.example.main.mapper;

import com.example.main.dto.EventRequestStatusUpdateResult;
import com.example.main.dto.ParticipationRequestDto;
import com.example.main.repository.model.Event;
import com.example.main.repository.model.Request;
import com.example.main.repository.model.RequestStatus;
import com.example.main.repository.model.User;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RequestMapper {
    public Request toRequest(User requester, Event event) {
        return new Request(
                null,
                LocalDateTime.now(),
                event,
                requester,
                event.getParticipantLimit() == 0 || !event.getRequestModeration() ? RequestStatus.CONFIRMED : RequestStatus.PENDING
        );
    }

    public ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }

    public EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<Request> requests) {
        List<Request> confirmedRequests = requests.stream().filter(request -> request.getStatus().equals(RequestStatus.CONFIRMED)).collect(Collectors.toList());
        List<Request> rejectedRequests = requests.stream().filter(request -> request.getStatus().equals(RequestStatus.REJECTED)).collect(Collectors.toList());

        return toEventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    public abstract List<ParticipationRequestDto> toParticipationRequestDto(List<Request> requests);

    public abstract EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<Request> confirmedRequests, List<Request> rejectedRequests);
}
