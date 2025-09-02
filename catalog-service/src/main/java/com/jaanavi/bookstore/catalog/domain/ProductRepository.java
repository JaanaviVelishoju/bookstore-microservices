package com.jaanavi.bookstore.catalog.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    //    spring data jpa as drived query with common
    //    prefix with findBy and where clause
    Optional<ProductEntity> findByCode(String code);
}
