package com.example.main.controller;

import com.example.api.dto.CategoryDto;
import com.example.main.mapper.CategoryMapper;
import com.example.main.service.CategoryService;
import com.example.main.repository.OffsetBasedPageRequest;
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

import static com.example.api.Constant.PAGE_DEFAULT_FROM;
import static com.example.api.Constant.PAGE_DEFAULT_SIZE;

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
