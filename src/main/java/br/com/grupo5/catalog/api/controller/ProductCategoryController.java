package br.com.grupo5.catalog.api.controller;

import br.com.grupo5.catalog.api.dto.CategoryResponse;
import br.com.grupo5.catalog.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products/{productId}/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findCategoriesByProductId(@PathVariable UUID productId) {
        var product = productService.findById(productId);
        var categoriesResponseList = product.getCategories().stream().map(CategoryResponse::toDto).toList();

        return ResponseEntity.ok(categoriesResponseList);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> associateProductToCategory(@PathVariable UUID productId, @PathVariable UUID categoryId){
        productService.associateProductToCategory(productId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> disassociateProductToCategory(@PathVariable UUID productId, @PathVariable UUID categoryId){
        productService.disassociateProductToCategory(productId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
