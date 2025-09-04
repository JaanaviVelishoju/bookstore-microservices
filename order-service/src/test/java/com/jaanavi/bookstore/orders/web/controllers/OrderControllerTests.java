package com.jaanavi.bookstore.orders.web.controllers;

import com.jaanavi.bookstore.orders.testdata.TestDataFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import io.restassured.http.ContentType;
import com.jaanavi.bookstore.orders.AbstractIT;

class OrderControllerTests extends AbstractIT{

    @Nested
    class CreateOrderTests {

        @Test
        void shouldCreateOrderSuccessfully(){
//            below is muilti line string payload
            var payload = """
                    {
                        "customer" :{
                            "name":"Devika",
                            "email": "Devika@gmail.com",
                            "phone": "999999999"
                        },
                        "deliveryAddress" :{
                            "addressLine1":"kukatpally",
                            "addressLine2": "KPHB",
                            "city": "Hyderabad",
                             "state": "Telangana",
                              "zipCode": "",
                               "country": ""
                        },
                        "items" :[{
                            "code":"P100",
                            "name":"Product 1",
                            "price": 25.50,
                            "quantity": 1
                        }
                        ]
                     }
                    """;
            given().contentType(ContentType.JSON)

                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
//                    .header("Authorization", "Bearer " + getToken())
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}