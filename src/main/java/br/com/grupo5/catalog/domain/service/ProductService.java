package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.exception.EntityNotFoundException;
import br.com.grupo5.catalog.domain.model.Category;
import br.com.grupo5.catalog.domain.model.Product;
import br.com.grupo5.catalog.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final CategoryService categoryService;

    public List<Product> findAll(Specification<Product> productSpec, List<String> categoriesNames) {
        var products = repository.findAll(productSpec);

        if (!categoriesNames.isEmpty()) {

            List<Category> categoryList = categoriesNames.stream()
                    .map(categoryService::findByCategoryNameLike)
                    .flatMap(List::stream)
                    .toList();

            return products.stream().filter(product -> product.getCategories().containsAll(categoryList)).toList();
        }

        return products;
    }

    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    public Product findById(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Product not found for id: %s", id)));
    }

    @Transactional
    public void associateProductToCategory(UUID productId, UUID categoryId) {
        var product = findById(productId);
        var category = categoryService.findById(categoryId);

        product.associateCategory(category);
    }

    @Transactional
    public void disassociateProductToCategory(UUID productId, UUID categoryId) {
        var product = findById(productId);
        var category = categoryService.findById(categoryId);

        product.disassociateCategory(category);
    }

    @Transactional
    public void deleteById(UUID id) {
        var product = findById(id);
        repository.delete(product);
    }
}
