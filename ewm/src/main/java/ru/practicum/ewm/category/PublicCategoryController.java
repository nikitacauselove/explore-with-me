package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.CategoryMapper;
import ru.practicum.ewm.util.OffsetBasedPageRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_FROM;
import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class PublicCategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    public CategoryDto findById(@PathVariable @Positive Long catId) {
        return categoryMapper.toCategoryDto(categoryService.findById(catId));
    }

    @GetMapping
    public List<CategoryDto> findAll(@RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
                                     @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);

        return categoryMapper.toCategoryDto(categoryService.findAll(pageable));
    }
}
