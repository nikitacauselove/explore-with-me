package com.example.main.controller;

import com.example.api.dto.EventFullDto;
import com.example.api.dto.EventShortDto;
import com.example.main.client.StatisticsClient;
import com.example.main.mapper.EventMapper;
import com.example.api.dto.enums.EventSort;
import com.example.main.service.EventService;
import com.example.main.repository.OffsetBasedPageRequest;
import com.example.api.dto.constraint.RangeStartBeforeRangeEnd;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.api.Constant.PAGE_DEFAULT_FROM;
import static com.example.api.Constant.PAGE_DEFAULT_SIZE;
import static com.example.api.Constant.TIME_PATTERN;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class PublicEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;
    private final StatisticsClient statisticsClient;

    @Value("${app.name}")
    private String app;

    @GetMapping("/{id}")
    public EventFullDto findById(@PathVariable @Positive Long id, HttpServletRequest request) {
        statisticsClient.postEndpointHit(app, request);

        return eventMapper.toEventFullDto(eventService.findByIdForPublic(id));
    }

    @GetMapping
    @RangeStartBeforeRangeEnd
    public List<EventShortDto> findAll(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) List<Long> categories,
                                       @RequestParam(required = false) Boolean paid,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = TIME_PATTERN) LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                       @RequestParam(required = false) EventSort sort,
                                       @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
                                       @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size,
                                       HttpServletRequest request) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        statisticsClient.postEndpointHit(app, request);

        return eventMapper.toEventShortDto(eventService.findAll(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, pageable));
    }
}
