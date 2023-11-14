package br.com.grupo5.catalog.api.controller;

import br.com.grupo5.catalog.api.dto.CategorySaveRequest;
import br.com.grupo5.catalog.api.dto.CategoryUpdateRequest;
import br.com.grupo5.catalog.domain.model.Category;
import br.com.grupo5.catalog.domain.service.CategoryService;
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
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CategorySaveRequest request) {
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
    public ResponseEntity<Category> update(@PathVariable UUID id, @RequestBody CategoryUpdateRequest request) {
        var category = service.findById(id);
        request.copyToModel(category);

        category = service.save(category);

        return ResponseEntity.ok(category);
    }
}
