package br.com.grupo5.catalog.api.controller;

import br.com.grupo5.catalog.api.dto.CategoryModel;
import br.com.grupo5.catalog.api.dto.CategoryRequest;
import br.com.grupo5.catalog.domain.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryModel>> findAll() {
        var categoryResponseList = service.findAll().stream().map(CategoryModel::of).toList();
        return ResponseEntity.ok(categoryResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryModel> findById(@PathVariable UUID id) {
        var category = service.findById(id);
        return ResponseEntity.ok(CategoryModel.of(category));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid CategoryRequest request) {
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
    public ResponseEntity<CategoryModel> update(@PathVariable UUID id, @RequestBody @Valid CategoryRequest request) {
        var category = service.findById(id);
        BeanUtils.copyProperties(request, category);

        category = service.save(category);
        return ResponseEntity.ok(CategoryModel.of(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
