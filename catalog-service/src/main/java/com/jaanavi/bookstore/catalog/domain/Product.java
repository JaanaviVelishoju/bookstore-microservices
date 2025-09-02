package com.jaanavi.bookstore.catalog.domain;

import java.math.BigDecimal;

// we sharing to web layer so,it is public
public record Product(String code, String name, String description, String imageUrl, BigDecimal price) {}
