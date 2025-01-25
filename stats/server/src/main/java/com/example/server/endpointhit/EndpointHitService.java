package com.example.server.endpointhit;

import com.example.server.endpointhit.dto.ViewStatisticsDto;
import com.example.server.endpointhit.model.EndpointHit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    public EndpointHit create(EndpointHit endpointHit) {
        return endpointHitRepository.save(endpointHit);
    }

    public List<ViewStatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return endpointHitRepository.getStatistics(start, end, uris, unique);
    }
}
