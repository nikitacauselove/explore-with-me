package ru.practicum.ewm.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventIdAndEventInitiatorId(Long eventId, Long initiatorId);

    List<Request> findAllByIdInAndEventIdAndEventInitiatorId(List<Long> ids, Long eventId, Long initiatorId);
}
