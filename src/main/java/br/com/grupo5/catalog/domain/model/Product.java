package br.com.grupo5.catalog.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String description;
    private Boolean available;
    private BigDecimal price;

    @OneToMany
    private Set<Picture> pictures = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Product(String name, String description, Boolean available, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.price = price;
    }

    public void associateCategory(Category category) {
        getCategories().add(category);
    }

    public void disassociateCategory(Category category) {
        getCategories().remove(category);
    }
}
