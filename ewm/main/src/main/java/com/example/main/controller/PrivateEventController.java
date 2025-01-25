package com.example.main.controller;

import com.example.api.dto.EventFullDto;
import com.example.api.dto.EventRequestStatusUpdateRequest;
import com.example.api.dto.EventRequestStatusUpdateResult;
import com.example.api.dto.EventShortDto;
import com.example.api.dto.NewEventDto;
import com.example.api.dto.ParticipationRequestDto;
import com.example.api.dto.UpdateEventUserRequest;
import com.example.main.mapper.EventMapper;
import com.example.main.mapper.RequestMapper;
import com.example.main.repository.model.Event;
import com.example.main.repository.model.Request;
import com.example.main.repository.model.User;
import com.example.main.service.EventService;
import com.example.main.service.RequestService;
import com.example.main.service.UserService;
import com.example.main.repository.OffsetBasedPageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.api.Constant.PAGE_DEFAULT_FROM;
import static com.example.api.Constant.PAGE_DEFAULT_SIZE;

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
