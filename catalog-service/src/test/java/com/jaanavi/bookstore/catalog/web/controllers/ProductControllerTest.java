package com.jaanavi.bookstore.catalog.web.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.jaanavi.bookstore.catalog.AbstractIT;
import com.jaanavi.bookstore.catalog.domain.Product;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

// when we run this ,spins up postgres contsiner in docker, and fyway migrations are applied
// and data will be inserted ,excecute test and verify the response.

@Sql("/test-data.sql") // inserting dummy data for testing for every method in this class
// trucating (any data is added while testing is removed,bcz it doeanot effect for other tesing methods )
// //it before each it and adding required data
class ProductControllerTest extends AbstractIT {

    @Test
    void shouldReturnProducts() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("data", hasSize(10)) // we given as defult as 10
                .body("totalElements", is(15)) // total no.of data rows are 15 in db.migration
                .body("pageNumber", is(1))
                .body("totalPages", is(2))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("hasNext", is(true))
                .body("hasPrevious", is(false));
    }

    @Test
    void shouldGetProductsByCode() {
        Product product = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "P100")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(Product.class);

        // asserting/comparing response
        assertThat(product.code()).isEqualTo("P100");
        assertThat(product.name()).isEqualTo("The Hunger Games");
        assertThat(product.description()).isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(product.price()).isEqualTo(new BigDecimal("34.0"));
    }

    @Test
    void shouldReturnNotFoundWhenProductCodeNotExists() {
        String code = "invalid_product_code";
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", code)
                .then()
                .statusCode(404)
                .body("status", is(404))
                .body("title", is("Product Not Found"))
                .body("detail", is("Product with code " + code + " not found"));
    }
}
