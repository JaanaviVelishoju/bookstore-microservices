package com.jaanavi.bookstore.catalog.web.controllers;

import com.jaanavi.bookstore.catalog.domain.PagedResult;
import com.jaanavi.bookstore.catalog.domain.Product;
import com.jaanavi.bookstore.catalog.domain.ProductNotFoundException;
import com.jaanavi.bookstore.catalog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// remove public from controller class, bcz spring boot is only going to use/invoke this

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
