package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.request.model.RequestUpdateStatus;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestUpdateStatus status;
}
