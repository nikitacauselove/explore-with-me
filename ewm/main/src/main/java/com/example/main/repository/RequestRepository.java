package com.example.main.repository;

import com.example.main.repository.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventIdAndEventInitiatorId(Long eventId, Long initiatorId);

    List<Request> findAllByIdInAndEventIdAndEventInitiatorId(List<Long> ids, Long eventId, Long initiatorId);
}
