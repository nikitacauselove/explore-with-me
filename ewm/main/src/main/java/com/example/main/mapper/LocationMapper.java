package com.example.main.mapper;

import com.example.main.dto.LocationDto;
import com.example.main.repository.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
