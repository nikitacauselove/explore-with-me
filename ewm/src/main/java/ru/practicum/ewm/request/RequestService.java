package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventStatus;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.exception.NotAvailableException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class RequestService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    public Request create(Long requesterId, Event event, Request request) {
        if (requesterId.equals(event.getInitiator().getId())) {
            throw new NotAvailableException("Event initiator cannot add a request to participate in its event");
        }
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new NotAvailableException("You cannot participate in an unpublished event");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() - event.getConfirmedRequests() == 0) {
            throw new NotAvailableException("The participant limit has been reached");
        }
        if (request.getStatus() == RequestStatus.CONFIRMED) {
            eventRepository.updateConfirmedRequests(event.getId(), 1L);
        }

        return requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public Request findById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id=%s was not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Request> findAllByRequesterId(Long requesterId) {
        return requestRepository.findAllByRequesterId(requesterId);
    }

    @Transactional(readOnly = true)
    public List<Request> findAllByIdInAndEventIdAndEventInitiatorId(List<Long> ids, Long eventId, Long initiatorId) {
        return requestRepository.findAllByIdInAndEventIdAndEventInitiatorId(ids, eventId, initiatorId);
    }

    public Request cancel(Long userId, Request request, Long eventId) {
        if (userId.equals(request.getRequester().getId())) {
            request.setStatus(RequestStatus.CANCELED);
            eventRepository.updateConfirmedRequests(eventId, -1L);

            return requestRepository.save(request);
        }
        throw new ValidationException(String.format("User with id=%s didn't create request with id=%s", userId, request.getId()));
    }
}
