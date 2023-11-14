package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Category;
import lombok.Data;

@Data
public class CategorySaveRequest {

    private String name;

    public Category toModel() {
        return new Category(name);
    }
}
