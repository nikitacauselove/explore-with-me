package com.example.main.service;

import com.example.main.exception.NotAvailableException;
import com.example.main.exception.NotFoundException;
import com.example.main.repository.CategoryRepository;
import com.example.main.repository.EventRepository;
import com.example.main.repository.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%s was not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }

    public void deleteById(Long id) {
        if (eventRepository.existsByCategoryId(id)) {
            throw new NotAvailableException("The category is not empty");
        }
        categoryRepository.deleteById(id);
    }
}
