package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryUpdateRequest {

    private String name;

    public void copyToModel(Category category) {
        category.setName(name);
    }
}
