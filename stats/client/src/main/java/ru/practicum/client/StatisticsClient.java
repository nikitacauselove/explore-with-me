package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatisticsDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ru.practicum.dto.EndpointHitDto.DATE_TIME_FORMATTER;

@RequiredArgsConstructor
@Service
public class StatisticsClient {
    @Value("${stats-server.url}")
    private String statisticsServerUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void postEndpointHit(String app, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(EndpointHitDto.DATE_TIME_FORMATTER);
        EndpointHitDto endpointHitDto = new EndpointHitDto(app, request.getRequestURI(), request.getRemoteAddr(), timestamp);

        restTemplate.postForLocation(statisticsServerUrl.concat("/hit"), endpointHitDto);
    }

    public List<ViewStatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String url = statisticsServerUrl.concat("/stats?start={start}&end={end}&uris={uris}&unique={unique}");
        Map<String, ?> uriVariables = Map.of(
                "start", start.format(DATE_TIME_FORMATTER),
                "end", end.format(DATE_TIME_FORMATTER),
                "uris", String.join(",", uris),
                "unique", unique);
        ViewStatisticsDto[] response = restTemplate.getForObject(url, ViewStatisticsDto[].class, uriVariables);

        return response == null ? Collections.emptyList() : List.of(response);
    }
}