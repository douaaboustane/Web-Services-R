package com.bench.variantec.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name="item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Item {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=64)
    private String sku;

    @Column(nullable=false, length=128)
    private String name;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal price;

    @Column(nullable=false)
    private Integer stock;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id", nullable=false)
    @JsonIgnore // on n’essaie pas de sérialiser la relation (évite LazyInitializationException)
    private Category category;

    @Column(name="updated_at", nullable=false)
    private Instant updatedAt = Instant.now();

    // Pour afficher l'id de catégorie dans la réponse JSON
    @Transient
    @JsonProperty("categoryId")
    public Long getCategoryId() { return (category != null) ? category.getId() : null; }
}
