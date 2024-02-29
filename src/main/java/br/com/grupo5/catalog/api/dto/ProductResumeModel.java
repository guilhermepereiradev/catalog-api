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
public class ProductResumeModel {

    private UUID id;
    private String name;
    private String description;
    private Boolean available;
    private BigDecimal price;
    private Set<Category> categories;

    public static ProductResumeModel of(Product product) {
        return new ProductResumeModel(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailable(),
                product.getPrice(),
                product.getCategories());
    }

}