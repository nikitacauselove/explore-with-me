package com.example.main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EventDtoWithViews {
    private Long id;
    private Long views;
}
