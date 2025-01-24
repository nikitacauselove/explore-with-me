package com.example.main.controller;

import com.example.main.dto.EventFullDto;
import com.example.main.dto.UpdateEventAdminRequest;
import com.example.main.mapper.EventMapper;
import com.example.main.repository.model.Event;
import com.example.main.repository.model.EventStatus;
import com.example.main.service.EventService;
import com.example.main.util.OffsetBasedPageRequest;
import com.example.main.util.validation.RangeStartBeforeRangeEnd;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.main.util.Constant.PAGE_DEFAULT_FROM;
import static com.example.main.util.Constant.PAGE_DEFAULT_SIZE;
import static com.example.main.util.Constant.TIME_PATTERN;

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
