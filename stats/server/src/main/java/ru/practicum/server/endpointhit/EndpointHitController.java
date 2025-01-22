package ru.practicum.server.endpointhit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatisticsDto;
import ru.practicum.server.endpointhit.dto.EndpointHitMapper;
import ru.practicum.server.endpointhit.model.EndpointHit;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto create(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.toEndpointHit(endpointHitDto);

        log.info("Сохранение информации о том, что на uri {} сервиса {} был отправлен запрос пользователем c IP {}.", endpointHit.getUri(), endpointHit.getApp(), endpointHit.getIp());
        return EndpointHitMapper.toEndpointHitDto(endpointHitService.create(endpointHit));
    }

    @GetMapping("/stats")
    public List<ViewStatisticsDto> getStatistics(@RequestParam @DateTimeFormat(pattern = EndpointHitDto.DATE_TIME_PATTERN) LocalDateTime start,
                                                 @RequestParam @DateTimeFormat(pattern = EndpointHitDto.DATE_TIME_PATTERN) LocalDateTime end,
                                                 @RequestParam(required = false) List<String> uris,
                                                 @RequestParam(defaultValue = "false") boolean unique) {
        if (start.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End cannot be earlier than start");
        }

        log.info("Получение статистики по посещениям.");
        return endpointHitService.getStatistics(start, end, uris, unique);
    }
}
