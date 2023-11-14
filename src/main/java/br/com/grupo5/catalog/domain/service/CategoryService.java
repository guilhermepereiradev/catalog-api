package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.exception.EntityNotFoundException;
import br.com.grupo5.catalog.domain.model.Category;
import br.com.grupo5.catalog.domain.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category not found for id: %s", id)));
    }

    @Transactional
    public Category save(Category category) {
        return repository.save(category);
    }
}
