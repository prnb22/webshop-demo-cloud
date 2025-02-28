package com.cloud.webshop.repository;

import com.cloud.webshop.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT inv FROM Inventory inv WHERE inv.product.productId = :productId")
    List<Inventory> findByProductId(Long productId);
}
