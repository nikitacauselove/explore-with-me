package com.example.api.dto;

import com.example.api.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class FullCommentDto {
    private Long id;
    private String content;
    private UserShortDto author;

    @JsonFormat(pattern = Constant.TIME_PATTERN)
    private LocalDateTime created;

    @JsonFormat(pattern = Constant.TIME_PATTERN)
    private LocalDateTime updated;
}
