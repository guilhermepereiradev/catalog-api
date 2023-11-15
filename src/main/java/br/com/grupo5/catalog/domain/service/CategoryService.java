package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.exception.BusinessRuleException;
import br.com.grupo5.catalog.domain.exception.EntityNotFoundException;
import br.com.grupo5.catalog.domain.model.Category;
import br.com.grupo5.catalog.domain.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Transactional
    public void deleteById(UUID id) {
        try {
            repository.delete(findById(id));
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new BusinessRuleException("Cannot delete category because it is in use");
        }
    }

    public List<Category> findByCategoryNameLike(String name){
        return repository.findByNameLike(name);
    }
}
