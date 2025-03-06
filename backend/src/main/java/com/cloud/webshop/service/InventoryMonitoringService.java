package com.cloud.webshop.service;

import com.cloud.webshop.model.Inventory;
import com.cloud.webshop.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryMonitoringService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryMonitoringService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EmailService emailService;

    // Run every 5 minutes
    @Scheduled(fixedRate = 300000) // 5 minutes = 5 * 60 * 1000 milliseconds
    public void checkLowInventoryLevels() {
        logger.info("Starting inventory check at: {}", LocalDateTime.now().format(formatter));
        
        List<Inventory> lowStockInventory = inventoryRepository.findByStockLevelLessThan(11);
        
        if (!lowStockInventory.isEmpty()) {
            logger.warn("Found {} products with low stock", lowStockInventory.size());
            sendLowStockNotification(lowStockInventory);
        } else {
            logger.info("No products with low stock found");
        }
    }

    private void sendLowStockNotification(List<Inventory> lowStockInventory) {
        Context context = new Context();
        
        // Create a list of product details for the email
        List<ProductStockInfo> productList = lowStockInventory.stream()
            .map(inventory -> new ProductStockInfo(
                inventory.getProduct().getTitle(),
                inventory.getStockLevel(),
                inventory.getSupplier().getName()
            ))
            .collect(Collectors.toList());

        context.setVariable("products", productList);
        context.setVariable("totalProducts", productList.size());
        context.setVariable("checkTime", LocalDateTime.now().format(formatter));

        // Send to admin email (you should configure this in application.properties)
        emailService.sendEmail(
            "s.pabon93@gmail.com",
            "Low Stock Alert - Action Required",
            "low-stock-alert",
            context
        );
        
        logger.info("Low stock notification email sent successfully");
    }

    // Helper class to format product information for the email template
    private static class ProductStockInfo {
        private final String productName;
        private final int currentStock;
        private final String supplierName;

        public ProductStockInfo(String productName, int currentStock, String supplierName) {
            this.productName = productName;
            this.currentStock = currentStock;
            this.supplierName = supplierName;
        }

        public String getProductName() {
            return productName;
        }

        public int getCurrentStock() {
            return currentStock;
        }

        public String getSupplierName() {
            return supplierName;
        }
    }
} 