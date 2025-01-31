package com.cloud.webshop.controller;

import com.cloud.webshop.model.Product;
import com.cloud.webshop.response.ProductResponse;
import com.cloud.webshop.response.ApiResponse;
import com.cloud.webshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    @Operation(
            summary = "Get all products",
            description = "Retrieve a paginated list of all products."
    )
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Create a Pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated products
        Page<Product> productPage = productService.getAllProducts(pageable);

        // Convert products to DTOs
        List<ProductResponse> products = productPage.getContent().stream()
                .map(ProductResponse::toProductResponse)
                .collect(Collectors.toList());

        // Create the response with pagination details
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>(
                "success",
                "Products retrieved successfully",
                products,
                productPage.getNumber(),      // Current page number
                productPage.getSize(),        // Page size
                productPage.getTotalPages(),  // Total pages
                productPage.getTotalElements() // Total items
        );

        return ResponseEntity.ok(response);
    }

}
