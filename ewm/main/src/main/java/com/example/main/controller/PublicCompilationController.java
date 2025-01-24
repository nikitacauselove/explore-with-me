package com.example.main.controller;

import com.example.main.dto.CompilationDto;
import com.example.main.mapper.CompilationMapper;
import com.example.main.service.CompilationService;
import com.example.main.util.OffsetBasedPageRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.main.util.Constant.PAGE_DEFAULT_FROM;
import static com.example.main.util.Constant.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
public class PublicCompilationController {
    private final CompilationMapper compilationMapper;
    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    public CompilationDto findById(@PathVariable @Positive Long compId) {
        return compilationMapper.toCompilationDto(compilationService.findById(compId));
    }

    @GetMapping
    public List<CompilationDto> findAll(@RequestParam(required = false) Boolean pinned,
                                        @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);

        return compilationMapper.toCompilationDto(compilationService.findAll(pinned, pageable));
    }
}
