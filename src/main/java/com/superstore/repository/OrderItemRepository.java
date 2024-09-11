package com.superstore.repository;

import com.superstore.dto.ProductAwaitingPaymentReportDto;
import com.superstore.dto.ProductReportDto;
import com.superstore.dto.ProfitReportDto;
import com.superstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT new com.superstore.dto.ProductReportDto(p.productId, p.name, SUM(oi.quantity)) " +
            "FROM Product p JOIN OrderItem oi ON p.productId = oi.product.productId " +
            "JOIN Order o ON oi.order.orderId = o.orderId " +
            "WHERE o.status = 'DELIVERED' " +
            "GROUP BY p.productId, p.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<ProductReportDto> findTop10SoldProducts();

    @Query("SELECT new com.superstore.dto.ProductReportDto(p.productId, p.name, COUNT(oi)) " +
            "FROM Product p JOIN OrderItem oi ON p.productId = oi.product.productId " +
            "JOIN Order o ON oi.order.orderId = o.orderId " +
            "WHERE o.status = 'CANCELLED' " +
            "GROUP BY p.productId, p.name " +
            "ORDER BY COUNT(oi) DESC")
    List<ProductReportDto> findTop10CancelledProducts();

    @Query("SELECT date_trunc(:interval, o.createdAt) AS truncatedDate, " +
            "SUM(oi.quantity * oi.priceAtPurchase) AS totalProfit " +
            "FROM Order o JOIN OrderItem oi ON o.orderId = oi.order.orderId " +
            "WHERE o.status = 'DELIVERED' " +
            "AND o.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY truncatedDate")
    List<Object[]> findProfitByInterval(@Param("interval") String interval,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);
/*

    @Query("SELECT new com.superstore.dto.ProductReport2Dto(p.productId, p.name, o.createdAt) " +
            "FROM Product p JOIN OrderItem oi ON p.product.productId = oi.product.productId " +
            "JOIN Order o ON oi.order.orderId = o.orderId " +
            "WHERE o.status = 'PROCESSING' AND o.createdAt < :thresholdDate")
    List<ProductReport2Dto> findOrdersProcessingBeforeDate(@Param("thresholdDate") LocalDateTime thresholdDate);

*/

    @Query("SELECT new com.superstore.dto.ProductAwaitingPaymentReportDto(p.productId, p.name, o.createdAt) " +
            "FROM Product p " +
            "JOIN OrderItem oi ON p.productId = oi.product.productId " +
            "JOIN Order o ON oi.order.orderId = o.orderId " +
            "WHERE o.status = 'AWAITING_PAYMENT' AND o.createdAt < :dateThreshold")
    List<ProductAwaitingPaymentReportDto> findProductsAwaitingPaymentForMoreThanNDays(@Param("dateThreshold") LocalDateTime dateThreshold);
}

