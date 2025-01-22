package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.CategoryMapper;
import ru.practicum.ewm.category.model.Category;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        Category category = categoryService.create(categoryMapper.toCategory(newCategoryDto));

        return categoryMapper.toCategoryDto(category);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@PathVariable @Positive Long catId, @RequestBody @Valid NewCategoryDto newCategoryDto) {
        Category category = categoryMapper.toCategory(categoryService.findById(catId), newCategoryDto);

        return categoryMapper.toCategoryDto(categoryService.update(category));
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Positive Long catId) {
        Category category = categoryService.findById(catId);

        categoryService.deleteById(category.getId());
    }
}
