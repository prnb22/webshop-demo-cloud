package com.cloud.webshop.response;

import com.cloud.webshop.model.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Long productId;
    private String title;
    private String summary;
    private String description;
    private String category;
    private Double price;
    private String imageUrl;

    // Convert Product entity to ProductResponse
    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
                .summary(product.getSummary())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
