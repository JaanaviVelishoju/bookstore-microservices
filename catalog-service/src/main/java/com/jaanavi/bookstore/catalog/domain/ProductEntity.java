package com.jaanavi.bookstore.catalog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Product code is required") private String code;

    @NotBlank(message = "Product name is required") @Column(nullable = false)
    private String name;

    private String description;

    private String imageUrl;

    @NotNull(message = "Product price is required") @DecimalMin("0.1") @Column(nullable = false)
    private BigDecimal price;

    public ProductEntity() {}

    public ProductEntity(String code, Long id, String name, String description, BigDecimal price, String imageUrl) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Product code is required") String getCode() {
        return code;
    }

    public void setCode(@NotBlank(message = "Product code is required") String code) {
        this.code = code;
    }

    public @NotBlank(message = "Product name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Product name is required") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotNull(message = "Product price is required") @DecimalMin("0.1") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Product price is required") @DecimalMin("0.1") BigDecimal price) {
        this.price = price;
    }
}
