package ru.practicum.ewm.category.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

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
