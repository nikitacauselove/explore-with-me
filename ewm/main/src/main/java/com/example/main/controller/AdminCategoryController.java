package com.example.main.controller;

import com.example.api.dto.CategoryDto;
import com.example.api.dto.NewCategoryDto;
import com.example.main.mapper.CategoryMapper;
import com.example.main.repository.model.Category;
import com.example.main.service.CategoryService;
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
