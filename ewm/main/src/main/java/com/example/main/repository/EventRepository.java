package com.example.main.repository;

import com.example.main.repository.model.Event;
import com.example.main.repository.model.EventStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByCategoryId(Long categoryId);

    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);

    List<Event> findAllByIdIn(List<Long> ids);

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @Query("select event " +
            "from Event as event " +
            "where (coalesce(:users, null) is null or event.initiator.id in :users) " +
            "and (coalesce(:states, null) is null or event.state in :states) " +
            "and (coalesce(:categories, null) is null or event.category.id in :categories) " +
            "and (coalesce(:rangeStart, null) is null or event.eventDate >= :rangeStart) " +
            "and (coalesce(:rangeEnd, null) is null or event.eventDate <= :rangeEnd)")
    List<Event> findAllByAdmin(@Param("users") List<Long> users,
                               @Param("states") List<EventStatus> states,
                               @Param("categories") List<Long> categories,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd,
                               Pageable pageable);

    @Query("select event " +
            "from Event as event " +
            "where event.state = 'PUBLISHED' " +
            "and (coalesce(:text, null) is null or (lower(event.annotation) like lower(concat('%', :text, '%')) or lower(event.description) like lower(concat('%', :text, '%')))) " +
            "and (coalesce(:categories, null) is null or event.category.id in :categories) " +
            "and (coalesce(:paid, null) is null or event.paid = :paid) " +
            "and (coalesce(:rangeStart, null) is null or event.eventDate >= :rangeStart) " +
            "and (coalesce(:rangeEnd, null) is null or event.eventDate <= :rangeEnd) " +
            "and (:onlyAvailable = false or event.id in " +
                "(select request.event.id " +
                "from Request as request " +
                "where request.status = 'CONFIRMED' " +
                "group by request.event.id " +
                "having 0 < event.participantLimit - count(request.id) " +
                "order by count(request.id))" +
            ")")
    List<Event> findAllPublic(@Param("text") String text,
                              @Param("categories") List<Long> categories,
                              @Param("paid") Boolean paid,
                              @Param("rangeStart") LocalDateTime rangeStart,
                              @Param("rangeEnd") LocalDateTime rangeEnd,
                              @Param("onlyAvailable") Boolean onlyAvailable,
                              Pageable pageable);

    @Query("select min(publishedOn) " +
            "from Event " +
            "where id in :ids")
    Optional<LocalDateTime> findMinPublishedOn(@Param("ids") List<Long> ids);

    @Modifying(flushAutomatically = true)
    @Query("update Event " +
            "set confirmedRequests = confirmedRequests + :value " +
            "where id = :id")
    void updateConfirmedRequests(@Param("id") Long id, @Param("value") Long value);
}
