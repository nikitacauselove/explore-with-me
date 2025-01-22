package ru.practicum.ewm.compilation.model;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.request.dto.UpdateCompilationRequest;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.event.EventService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public abstract class CompilationMapper {
    @Autowired
    private EventService eventService;

    public Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return new Compilation(
                null,
                newCompilationDto.getEvents() == null ? new ArrayList<>() : eventService.findAllByIdIn(newCompilationDto.getEvents()),
                newCompilationDto.getPinned(),
                newCompilationDto.getTitle()
        );
    }

    public Compilation toCompilation(Compilation compilation, UpdateCompilationRequest updateCompilationRequest) {
        return new Compilation(
                compilation.getId(),
                updateCompilationRequest.getEvents() == null ? compilation.getEvents() : eventService.findAllByIdIn(updateCompilationRequest.getEvents()),
                updateCompilationRequest.getPinned() == null ? compilation.getPinned() : updateCompilationRequest.getPinned(),
                updateCompilationRequest.getTitle() == null ? compilation.getTitle() : updateCompilationRequest.getTitle()
        );
    }

    public abstract CompilationDto toCompilationDto(Compilation compilation);

    public abstract List<CompilationDto> toCompilationDto(List<Compilation> compilations);
}
