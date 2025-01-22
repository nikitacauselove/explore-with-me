package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventSort;
import ru.practicum.ewm.event.model.EventStatus;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.*;
import ru.practicum.ewm.request.RequestRepository;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.request.dto.UpdateEventUserRequest;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;

    @Value("${app.name}")
    private String app;

    public Event create(Event event) {
        locationRepository.save(event.getLocation());

        return eventRepository.save(event);
    }

    public Event update(Event event, UpdateEventUserRequest request) {
        if (event.getState().equals(EventStatus.PUBLISHED)) {
            throw new NotAvailableException("Only pending or canceled events can be changed");
        }
        if (request.getStateAction() != null && request.getStateAction() == UserStateAction.SEND_TO_REVIEW) {
            event.setState(EventStatus.PENDING);
        }
        if (request.getStateAction() != null && request.getStateAction() == UserStateAction.CANCEL_REVIEW) {
            event.setState(EventStatus.CANCELED);
        }
        locationRepository.save(event.getLocation());

        return eventRepository.save(event);
    }

    public List<Request> updateRequests(Event event, EventRequestStatusUpdateRequest updateRequest, List<Request> requests) {
        if (updateRequest.getStatus().equals(RequestUpdateStatus.CONFIRMED) && event.getParticipantLimit() - event.getConfirmedRequests() == 0) {
            throw new NotAvailableException("The participant limit has been reached");
        }
        switch (updateRequest.getStatus()) {
            case CONFIRMED:
                requests.forEach(request -> {
                    if (!request.getStatus().equals(RequestStatus.PENDING)) {
                        throw new NotAvailableException("Request's status has to be PENDING");
                    }
                    if (0 < event.getParticipantLimit() - event.getConfirmedRequests()) {
                        request.setStatus(RequestStatus.CONFIRMED);
                        eventRepository.updateConfirmedRequests(event.getId(), 1L);
                    } else {
                        request.setStatus(RequestStatus.REJECTED);
                    }
                });
                break;
            case REJECTED:
                requests.forEach(request -> {
                    if (!request.getStatus().equals(RequestStatus.PENDING)) {
                        throw new NotAvailableException("Request's status has to be PENDING");
                    }
                    request.setStatus(RequestStatus.REJECTED);
                });
        }
        requestRepository.saveAll(requests);

        return requests;
    }

    public Event updateByAdmin(Event event, UpdateEventAdminRequest request) {
        if (request.getStateAction() != null) {
            if (!event.getState().equals(EventStatus.PENDING)) {
                throw new NotAvailableException(String.format("Cannot publish the event because it's not in the right state: %s", event.getState()));
            }
            if (request.getStateAction().equals(AdminStateAction.PUBLISH_EVENT)) {
                event.setState(EventStatus.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }
            if (request.getStateAction().equals(AdminStateAction.REJECT_EVENT)) {
                event.setState(EventStatus.CANCELED);
            }
        }
        if (event.getPublishedOn() != null && event.getEventDate().isBefore(event.getPublishedOn().plusHours(1))) {
            throw new NotAvailableException("The start date of the modified event must be no earlier than one hour from the publication date");
        }
        locationRepository.save(event.getLocation());

        return eventRepository.save(event);
    }












    @Transactional(readOnly = true)
    public Event findByIdAndInitiatorId(Long id, Long initiatorId) {
        return eventRepository.findByIdAndInitiatorId(id, initiatorId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with user id=%s was not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable) {
        return eventRepository.findAllByInitiatorId(initiatorId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Request> findAllRequests(Long eventId, Long initiatorId) {
        return requestRepository.findAllByEventIdAndEventInitiatorId(eventId, initiatorId);
    }

    @Transactional(readOnly = true)
    public List<Event> findAllForAdmin(List<Long> users, List<EventStatus> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable) {
        return eventRepository.findAllByAdmin(users, states, categories, rangeStart, rangeEnd, pageable);
    }

    @Transactional(readOnly = true)
    public List<Event> findAll(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable, EventSort sort, Pageable pageable) {
        return eventRepository.findAllPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
    }

    @Transactional(readOnly = true)
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Event> findAllByIdIn(List<Long> eventsId) {
        return eventRepository.findAllByIdIn(eventsId);
    }

    @Transactional(readOnly = true)
    public Event findByIdForPublic(Long id) {
        Event event = findById(id);

        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new NotFoundException("Event must be published");
        }
        return event;
    }
}
