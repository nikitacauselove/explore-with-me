package com.example.main.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
