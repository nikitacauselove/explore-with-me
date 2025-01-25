package com.example.main.mapper;

import com.example.api.dto.CategoryDto;
import com.example.api.dto.NewCategoryDto;
import com.example.main.repository.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    public Category toCategory(Category category, NewCategoryDto newCategoryDto) {
      return new Category(
              category.getId(),
              newCategoryDto.getName()
      );
    }

    public abstract Category toCategory(NewCategoryDto newCategoryDto);

    public abstract CategoryDto toCategoryDto(Category category);

    public abstract List<CategoryDto> toCategoryDto(List<Category> categories);
}
