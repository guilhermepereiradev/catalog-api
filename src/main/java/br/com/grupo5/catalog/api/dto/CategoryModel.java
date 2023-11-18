package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryModel {
    private UUID id;
    private String nome;

    public static CategoryModel toDto(Category category) {
        return new CategoryModel(category.getId(), category.getName());
    }
}
