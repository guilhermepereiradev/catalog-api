package br.com.grupo5.catalog.domain.repository.spec;

import br.com.grupo5.catalog.domain.model.Product;
import br.com.grupo5.catalog.domain.model.filter.ProductFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class ProductSpecs {

    private ProductSpecs() {}

    public static Specification<Product> filterProduct(ProductFilter filter) {
        return ((root, query, criteriaBuilder) -> {

            var categoriesJoin = root.join("categories");
            var predicates = new ArrayList<>();

            if(filter.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
            }

            if(filter.getDescription() != null) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + filter.getDescription() + "%"));
            }

            if(filter.getAvailable() != null) {
                predicates.add(criteriaBuilder.equal(root.get("available"), filter.getAvailable()));
            }

            if(filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if(filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            if(!filter.getCategories().isEmpty()) {
                var categoriesNamesPredicates = new ArrayList<Predicate>();

                filter.getCategories()
                        .forEach(categoryName ->
                            categoriesNamesPredicates.add(criteriaBuilder.like(categoriesJoin.get("name"), "%" + categoryName + "%"))
                        );

                predicates.add(criteriaBuilder.or(categoriesNamesPredicates.toArray(Predicate[]::new)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
