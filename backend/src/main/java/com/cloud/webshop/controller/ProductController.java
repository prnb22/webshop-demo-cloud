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
import java.util.Optional;
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
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        ApiResponse<List<ProductResponse>> response = productService.getAllProducts(page, size, keyword);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(
        summary = "Get product by ID",
        description = "Retrieve a specific product by its ID."
    )
        public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        ProductResponse productResponse = ProductResponse.toProductResponse(product.get());

        ApiResponse<ProductResponse> response = new ApiResponse<>(
                "success",
                "Product retrieved successfully",
                productResponse
        );

        return ResponseEntity.ok(response);
    }
}
