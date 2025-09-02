package com.jaanavi.bookstore.catalog.domain;

import com.jaanavi.bookstore.catalog.ApplicationProperties;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties properties;

    ProductService(ProductRepository productRepository, ApplicationProperties properties) {
        this.productRepository = productRepository;
        this.properties = properties;
    }

    public PagedResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        Page<Product> productsPage = productRepository.findAll(pageable).map(ProductMapper::toProduct);

        PagedResult<Product> pagedResult = new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
        return pagedResult;
    }

    public Optional<Product> getProductByCode(String code) {
        // to not leak our entity info ,so we are mapping to product record by using ProductMapper class
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }
}
