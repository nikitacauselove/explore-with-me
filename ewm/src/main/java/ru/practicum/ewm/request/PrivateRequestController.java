package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.user.UserService;

import javax.validation.constraints.Positive;
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
