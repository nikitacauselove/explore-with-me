package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.model.EventStatus;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.util.OffsetBasedPageRequest;
import ru.practicum.ewm.util.validation.RangeStartBeforeRangeEnd;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.util.Constant.*;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Validated
public class AdminEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable @Positive Long eventId, @RequestBody @Valid UpdateEventAdminRequest request) {
        Event event = eventMapper.toEvent(eventService.findById(eventId), request);

        return eventMapper.toEventFullDto(eventService.updateByAdmin(event, request));
    }

    @GetMapping
    @RangeStartBeforeRangeEnd
    public List<EventFullDto> findAll(@RequestParam(required = false) List<Long> users,
                                      @RequestParam(required = false) List<EventStatus> states,
                                      @RequestParam(required = false) List<Long> categories,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeStart,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeEnd,
                                      @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
                                      @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);

        return eventMapper.toEventFullDto(eventService.findAllForAdmin(users, states, categories, rangeStart, rangeEnd, pageable));
    }
}
