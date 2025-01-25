package com.example.api.dto;

import com.example.api.dto.enums.RequestUpdateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestUpdateStatus status;
}
