package com.example.main.service;

import com.example.main.exception.NotFoundException;
import com.example.main.repository.CompilationRepository;
import com.example.main.repository.model.Compilation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CompilationService {
    private final CompilationRepository compilationRepository;

    public Compilation create(Compilation compilation) {
        return compilationRepository.save(compilation);
    }

    public Compilation update(Compilation compilation) {
        return compilationRepository.save(compilation);
    }

    @Transactional(readOnly = true)
    public Compilation findById(Long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%s was not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Compilation> findAll(Boolean pinned, Pageable pageable) {
        return pinned == null ? compilationRepository.findAll(pageable).getContent() : compilationRepository.findAllByPinned(pinned, pageable);
    }

    public void deleteById(Long id) {
        compilationRepository.deleteById(id);
    }
}
