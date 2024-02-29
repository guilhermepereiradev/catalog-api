package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank
    private String name;

    public Category toModel() {
        return new Category(name);
    }
}
