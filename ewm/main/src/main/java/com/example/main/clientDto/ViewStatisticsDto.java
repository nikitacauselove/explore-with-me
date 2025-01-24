package com.example.main.clientDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ViewStatisticsDto {
    private String app;
    private String uri;
    private Long hits;
}
