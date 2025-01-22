package ru.practicum.ewm.event.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.event.dto.LocationDto;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
