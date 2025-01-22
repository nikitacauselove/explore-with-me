package ru.practicum.server.endpointhit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.ViewStatisticsDto;
import ru.practicum.server.endpointhit.model.EndpointHit;

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
