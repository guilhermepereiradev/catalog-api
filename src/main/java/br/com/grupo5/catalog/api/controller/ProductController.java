package br.com.grupo5.catalog.api.controller;

import br.com.grupo5.catalog.api.dto.ProductModel;
import br.com.grupo5.catalog.api.dto.ProductRequest;
import br.com.grupo5.catalog.api.dto.ProductResumeModel;
import br.com.grupo5.catalog.domain.model.filter.ProductFilter;
import br.com.grupo5.catalog.domain.repository.spec.ProductSpecs;
import br.com.grupo5.catalog.domain.service.ProductService;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResumeModel>> findAll(ProductFilter productFilter) {
        var productSpecification = ProductSpecs.filterProduct(productFilter);

        var productResponseList = productService.findAll(productSpecification, productFilter.getCategories())
                .stream().map(ProductResumeModel::of).toList();

        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable UUID id) {
        var product = productService.findById(id);
        return ResponseEntity.ok(ProductModel.of(product));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ProductRequest request) {
        var product = request.toModel();
        product = productService.save(product);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResumeModel> update(@PathVariable UUID id, @RequestBody @Valid ProductRequest request) {
        var product = productService.findById(id);
        BeanUtils.copyProperties(request, product);

        product = productService.save(product);

        return ResponseEntity.ok(ProductResumeModel.of(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
