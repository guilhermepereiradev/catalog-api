package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Category;
import br.com.grupo5.catalog.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductResponse {

    private UUID id;
    private String name;
    private String description;
    private Boolean available;
    private BigDecimal price;
    private Set<Category> categories;

    public static ProductResponse toDto(Product product) {
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailable(),
                product.getPrice(),
                product.getCategories());
    }

}