package com.example.server.endpointhit;

import com.example.server.endpointhit.dto.ViewStatisticsDto;
import com.example.server.endpointhit.model.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("select new com.example.server.endpointhit.dto.ViewStatisticsDto(hit.app, hit.uri, case when :unique = true then count(distinct(hit.ip)) else count(hit.ip) end) " +
            "from EndpointHit as hit " +
            "where hit.timestamp between :start and :end and (coalesce(:uris, null) is null or hit.uri in :uris) " +
            "group by hit.app, hit.uri " +
            "order by 3 desc")
    List<ViewStatisticsDto> getStatistics(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris, @Param("unique") Boolean unique);
}
