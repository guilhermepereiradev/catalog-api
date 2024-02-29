package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Boolean available;

    @Positive
    @NotNull
    private BigDecimal price;

    public Product toModel() {
        return new Product(name, description, available, price);
    }

}
