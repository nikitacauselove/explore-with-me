package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class LocationDto {
    @Min(-90) @Max(90)
    @NotNull
    private Float lat;

    @Min(-180) @Max(180)
    @NotNull
    private Float lon;
}
