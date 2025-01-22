package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.request.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.model.CompilationMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class AdminCompilationController {
    private final CompilationMapper compilationMapper;
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto);

        return compilationMapper.toCompilationDto(compilationService.create(compilation));
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable @Positive Long compId, @RequestBody @Valid UpdateCompilationRequest request) {
        Compilation compilation = compilationMapper.toCompilation(compilationService.findById(compId), request);

        return compilationMapper.toCompilationDto(compilationService.update(compilation));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Positive Long compId) {
        Compilation compilation = compilationService.findById(compId);

        compilationService.deleteById(compilation.getId());
    }
}
