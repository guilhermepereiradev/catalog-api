package br.com.grupo5.catalog.domain.model.filter;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductFilter {

    private String name;
    private String description;
    private Boolean available;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<String> categories = new ArrayList<>();
}
