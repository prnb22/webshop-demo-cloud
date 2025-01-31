package com.cloud.webshop.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    private int page;          // Current page number
    private int size;          // Number of items per page
    private int totalPages;    // Total number of pages
    private long totalItems;   // Total number of items

    // Constructor for success response with pagination
    public ApiResponse(String status, String message, T data, int page, int size, int totalPages, long totalItems) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    // Constructor for error response
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
        this.page = 0;
        this.size = 0;
        this.totalPages = 0;
        this.totalItems = 0;
    }
}
