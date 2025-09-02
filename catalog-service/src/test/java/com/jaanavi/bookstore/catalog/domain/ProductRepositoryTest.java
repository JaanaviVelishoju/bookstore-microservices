package com.jaanavi.bookstore.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

// this is a slice test annotation ,bcz we are testing only repository layer(one slice of our entrie app)
// by defult it needs in memory db(h2) for testing ,if not it fails,but we are using postgres
// and we are using testcontainers, so we test with that only,not h2.

@DataJpaTest(
        properties = {
            "spring.test.database.replace=none", // stoping to use inmemory db
            "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db", // spinup database for tesing
            // or
        })

// @Import(TestcontainersConfiguration.class) //connecting to db
@Sql("/test-data.sql") // setup the tesing data
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(15);
    }

    // testing with existing code
    @Test
    void shouldGetProductByCode() {
        ProductEntity product = productRepository.findByCode("P100").orElseThrow();
        // if it throws test fails

        //        asserting/comparing response
        assertThat(product.getCode()).isEqualTo("P100");
        assertThat(product.getName()).isEqualTo("The Hunger Games");
        assertThat(product.getDescription()).isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("34.0"));
    }

    // testing with non-exisxting code

    @Test
    void shouldReturnEmptyWhenProductCodeNotExists() {
        assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
    }
}
