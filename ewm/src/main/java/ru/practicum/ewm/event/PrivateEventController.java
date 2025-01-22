package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.UpdateEventUserRequest;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.RequestService;
import ru.practicum.ewm.user.UserService;
import ru.practicum.ewm.util.OffsetBasedPageRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_FROM;
import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
public class PrivateEventController {
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final EventService eventService;
    private final RequestService requestService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable @Positive Long userId, @RequestBody @Valid NewEventDto newEventDto) {
        User initiator = userService.findById(userId);
        Event event = eventMapper.toEvent(initiator, newEventDto);

        return eventMapper.toEventFullDto(eventService.create(event));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable @Positive Long userId, @PathVariable @Positive Long eventId, @RequestBody @Valid UpdateEventUserRequest request) {
        User initiator = userService.findById(userId);
        Event event = eventMapper.toEvent(eventService.findById(eventId), request);

        return eventMapper.toEventFullDto(eventService.update(event, request));
    }

    @GetMapping("/{eventId}")
    public EventFullDto findByIdAndInitiatorId(@PathVariable @Positive Long userId, @PathVariable @Positive Long eventId) {
        return eventMapper.toEventFullDto(eventService.findByIdAndInitiatorId(eventId, userId));
    }

    @GetMapping
    public List<EventShortDto> findAllByInitiatorId(@PathVariable @Positive Long userId,
                                                    @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
                                                    @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);

        return eventMapper.toEventShortDto(eventService.findAllByInitiatorId(userId, pageable));
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> findAllRequests(@PathVariable @Positive Long userId, @PathVariable @Positive Long eventId) {
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);

        return requestMapper.toParticipationRequestDto(eventService.findAllRequests(event.getId(), user.getId()));
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId, @RequestBody @Valid EventRequestStatusUpdateRequest updateRequest) {
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);
        List<Request> requests = requestService.findAllByIdInAndEventIdAndEventInitiatorId(updateRequest.getRequestIds(), event.getId(), user.getId());

        return requestMapper.toEventRequestStatusUpdateResult(eventService.updateRequests(event, updateRequest, requests));
    }
}
