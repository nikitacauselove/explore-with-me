package ru.practicum.server.endpointhit.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.server.endpointhit.model.EndpointHit;

import java.time.LocalDateTime;

@UtilityClass
public class EndpointHitMapper {
    public EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        LocalDateTime timestamp = LocalDateTime.parse(endpointHitDto.getTimestamp(), EndpointHitDto.DATE_TIME_FORMATTER);

        return new EndpointHit(null, endpointHitDto.getApp(), endpointHitDto.getUri(), endpointHitDto.getIp(), timestamp);
    }

    public EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return new EndpointHitDto(endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(), endpointHit.getTimestamp().toString());
    }
}
