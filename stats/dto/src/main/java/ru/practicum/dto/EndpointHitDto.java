package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EndpointHitDto {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @NotBlank
    @Size(max = 255)
    private String app;

    @NotBlank
    @Size(max = 255)
    private String uri;

    @NotBlank
    @Size(max = 255)
    private String ip;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    @NotNull
    private String timestamp;
}
