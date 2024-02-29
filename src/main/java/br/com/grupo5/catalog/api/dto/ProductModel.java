package br.com.grupo5.catalog.api.dto;

import br.com.grupo5.catalog.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProductModel {

    private UUID id;
    private String name;
    private String description;
    private Boolean available;
    private BigDecimal price;
    private Set<CategoryModel> categories;
    private Set<PictureModel> pictures;

    public static ProductModel of(Product product) {
        var categoriesModel = product.getCategories()
                .stream()
                .map(CategoryModel::of)
                .collect(Collectors.toSet());

        var picturesModel = product.getPictures()
                .stream()
                .map(PictureModel::of)
                .collect(Collectors.toSet());

        return new ProductModel(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailable(),
                product.getPrice(),
                categoriesModel,
                picturesModel);
    }

}