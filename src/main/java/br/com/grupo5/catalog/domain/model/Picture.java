package br.com.grupo5.catalog.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Picture {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String description;
    private String url;
    private String contentType;
    private Long size;

    @ManyToOne
    private Product product;
}
