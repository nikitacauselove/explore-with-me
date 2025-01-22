package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.util.OffsetBasedPageRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_FROM;
import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_SIZE;

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
