package com.jaanavi.bookstore.catalog.domain;

// we are using it as internall utility,so no need of public
class ProductMapper {

    static Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
    }
}
