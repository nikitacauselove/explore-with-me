package com.example.main.controller;

import com.example.api.dto.ParticipationRequestDto;
import com.example.main.mapper.RequestMapper;
import com.example.main.repository.model.Event;
import com.example.main.repository.model.Request;
import com.example.main.repository.model.User;
import com.example.main.service.EventService;
import com.example.main.service.RequestService;
import com.example.main.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Validated
public class PrivateRequestController {
    private final RequestMapper requestMapper;
    private final EventService eventService;
    private final RequestService requestService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable @Positive Long userId, @RequestParam @Positive Long eventId) {
        User requester = userService.findById(userId);
        Event event = eventService.findById(eventId);
        Request request = requestMapper.toRequest(requester, event);

        return requestMapper.toParticipationRequestDto(requestService.create(requester.getId(), event, request));
    }

    @GetMapping
    public List<ParticipationRequestDto> findAllByRequesterId(@PathVariable @Positive Long userId) {
        User requester = userService.findById(userId);

        return requestMapper.toParticipationRequestDto(requestService.findAllByRequesterId(requester.getId()));
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable @Positive Long userId, @PathVariable @Positive Long requestId) {
        User user = userService.findById(userId);
        Request request = requestService.findById(requestId);
        Event event = eventService.findById(request.getEvent().getId());

        return requestMapper.toParticipationRequestDto(requestService.cancel(user.getId(), request, event.getId()));
    }
}
