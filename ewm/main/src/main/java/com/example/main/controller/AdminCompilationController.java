package com.example.main.controller;

import com.example.api.dto.CompilationDto;
import com.example.api.dto.NewCompilationDto;
import com.example.api.dto.UpdateCompilationRequest;
import com.example.main.mapper.CompilationMapper;
import com.example.main.repository.model.Compilation;
import com.example.main.service.CompilationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
