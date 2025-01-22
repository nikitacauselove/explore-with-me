package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UpdateCompilationRequest {
    private List<Long> events;
    private Boolean pinned;

    @Length(min = 1, max = 50)
    private String title;
}
