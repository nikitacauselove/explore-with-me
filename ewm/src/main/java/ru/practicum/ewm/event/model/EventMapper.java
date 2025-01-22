package ru.practicum.ewm.event.model;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.client.StatisticsClient;
import ru.practicum.dto.ViewStatisticsDto;
import ru.practicum.ewm.category.model.CategoryMapper;
import ru.practicum.ewm.event.dto.EventDtoWithViews;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.request.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.request.dto.UpdateEventUserRequest;
import ru.practicum.ewm.user.model.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.category.CategoryService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class, LocationMapper.class})
public abstract class EventMapper {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StatisticsClient statisticsClient;
    @Autowired
    private LocationMapper locationMapper;

    public Event toEvent(User initiator, NewEventDto newEventDto) {
        return new Event(
                null,
                newEventDto.getAnnotation(),
                categoryService.findById(newEventDto.getCategory()),
                0L,
                LocalDateTime.now(),
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                initiator,
                locationMapper.toLocation(newEventDto.getLocation()),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                EventStatus.PENDING,
                newEventDto.getTitle()
        );
    }

    public Event toEvent(Event event, UpdateEventAdminRequest request) {
        return new Event(
                event.getId(),
                request.getAnnotation() == null ? event.getAnnotation() : request.getAnnotation(),
                request.getCategory() == null ? event.getCategory() : categoryService.findById(request.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                request.getDescription() == null ? event.getDescription() : request.getDescription(),
                request.getEventDate() == null ? event.getEventDate() : request.getEventDate(),
                event.getInitiator(),
                request.getLocation() == null ? event.getLocation() : locationMapper.toLocation(request.getLocation()),
                request.getPaid() == null ? event.getPaid() : request.getPaid(),
                request.getParticipantLimit() == null ? event.getParticipantLimit() : request.getParticipantLimit(),
                event.getPublishedOn(),
                request.getRequestModeration() == null ? event.getRequestModeration() : request.getRequestModeration(),
                event.getState(),
                request.getTitle() == null ? event.getTitle() : request.getTitle()
        );
    }

    public Event toEvent(Event event, UpdateEventUserRequest request) {
        return new Event(
                event.getId(),
                request.getAnnotation() == null ? event.getAnnotation() : request.getAnnotation(),
                request.getCategory() == null ? event.getCategory() : categoryService.findById(request.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                request.getDescription() == null ? event.getDescription() : request.getDescription(),
                request.getEventDate() == null ? event.getEventDate() : request.getEventDate(),
                event.getInitiator(),
                request.getLocation() == null ? event.getLocation() : locationMapper.toLocation(request.getLocation()),
                request.getPaid() == null ? event.getPaid() : request.getPaid(),
                request.getParticipantLimit() == null ? event.getParticipantLimit() : request.getParticipantLimit(),
                event.getPublishedOn(),
                request.getRequestModeration() == null ? event.getRequestModeration() : request.getRequestModeration(),
                event.getState(),
                request.getTitle() == null ? event.getTitle() : request.getTitle()
        );
    }

    public abstract EventFullDto toEventFullDto(Event event);

    public abstract List<EventFullDto> toEventFullDto(List<Event> events);

    public abstract EventShortDto toEventShortDto(Event event);

    public abstract List<EventShortDto> toEventShortDto(List<Event> events);

    @AfterMapping
    protected void toEventFullDtoAfterMapping(@MappingTarget EventFullDto dto) {
        setViews(List.of(dto));
    }

    @AfterMapping
    protected void toEventFullDtoAfterMapping(@MappingTarget List<EventFullDto> listOfDto) {
        setViews(listOfDto);
    }

    @AfterMapping
    protected void toEventShortDtoAfterMapping(@MappingTarget EventShortDto dto) {
        setViews(List.of(dto));
    }

    @AfterMapping
    protected void toEventShortDtoAfterMapping(@MappingTarget List<EventShortDto> listOfDto) {
        setViews(listOfDto);
    }

    private void setViews(@MappingTarget List<? extends EventDtoWithViews> listOfDto) {
        Map<Long, EventDtoWithViews> map = listOfDto.stream().collect(Collectors.toMap(EventDtoWithViews::getId, Function.identity()));
        List<Long> eventIds = List.copyOf(map.keySet());
        Optional<LocalDateTime> publishedOn = eventRepository.findMinPublishedOn(eventIds);

        if (publishedOn.isPresent()) {
            List<String> uris = eventIds.stream().map(id -> "/events/" + id).collect(Collectors.toList());
            List<ViewStatisticsDto> response = statisticsClient.getStatistics(publishedOn.get(), LocalDateTime.now(), uris, true);

            response.forEach(viewStatisticsDto -> {
                Long eventId = Long.parseLong(viewStatisticsDto.getUri().split("/")[2]);

                map.get(eventId).setViews(viewStatisticsDto.getHits());
            });
        }
    }
}
