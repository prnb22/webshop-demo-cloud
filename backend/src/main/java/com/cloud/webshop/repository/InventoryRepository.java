package com.cloud.webshop.repository;

import com.cloud.webshop.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT inv FROM Inventory inv WHERE inv.product.productId = :productId")
    List<Inventory> findByProductId(Long productId);

    @Query("SELECT inv FROM Inventory inv WHERE inv.product.productId in :productIds")
    List<Inventory> findByProductIdIn(List<Long> productIds);

    List<Inventory> findByStockLevelLessThan(int threshold);
}
