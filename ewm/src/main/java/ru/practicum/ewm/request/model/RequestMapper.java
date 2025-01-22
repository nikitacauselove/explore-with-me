package ru.practicum.ewm.request.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.model.User;

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
