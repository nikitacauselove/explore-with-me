package com.example.main.dto;

import com.example.main.repository.model.RequestUpdateStatus;
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
