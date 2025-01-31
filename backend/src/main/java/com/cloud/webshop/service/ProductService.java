package com.cloud.webshop.service;

import com.cloud.webshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
}
