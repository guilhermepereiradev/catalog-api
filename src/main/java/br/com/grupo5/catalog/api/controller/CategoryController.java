package br.com.grupo5.catalog.api.controller;

import br.com.grupo5.catalog.api.dto.CategoryResponse;
import br.com.grupo5.catalog.api.dto.CategorySaveRequest;
import br.com.grupo5.catalog.api.dto.CategoryUpdateRequest;
import br.com.grupo5.catalog.domain.model.Category;
import br.com.grupo5.catalog.domain.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        var categoryResponseList = service.findAll().stream().map(CategoryResponse::toDto).toList();
        return ResponseEntity.ok(categoryResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable UUID id) {
        var category = service.findById(id);
        return ResponseEntity.ok(CategoryResponse.toDto(category));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid CategorySaveRequest request) {
        var category = request.toModel();
        category = service.save(category);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable UUID id, @RequestBody @Valid CategoryUpdateRequest request) {
        var category = service.findById(id);
        request.copyToModel(category);

        category = service.save(category);

        return ResponseEntity.ok(CategoryResponse.toDto(category));
    }
}
