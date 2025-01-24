package com.example.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.example.main.util.Constant.TIME_PATTERN;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class FullCommentDto {
    private Long id;
    private String content;
    private UserShortDto author;

    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime created;

    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime updated;
}
