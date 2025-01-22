package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatisticsClient;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.EventSort;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.util.OffsetBasedPageRequest;
import ru.practicum.ewm.util.validation.RangeStartBeforeRangeEnd;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.util.Constant.*;

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
