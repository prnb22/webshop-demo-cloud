package com.cloud.webshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;
}
