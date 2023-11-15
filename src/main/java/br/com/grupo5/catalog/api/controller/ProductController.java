package br.com.grupo5.catalog.api.controller;

import br.com.grupo5.catalog.api.dto.ProductResponse;
import br.com.grupo5.catalog.api.dto.ProductSaveRequest;
import br.com.grupo5.catalog.domain.model.Product;
import br.com.grupo5.catalog.domain.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> productResponseList = productService.findAll().stream().map(ProductResponse::toDto).toList();

        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        var product = productService.findById(id);

        return ResponseEntity.ok(ProductResponse.toDto(product));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ProductSaveRequest request) {
        var product = request.toModel();
        product = productService.save(product);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
