package e3i2.ecommerce_backoffice.domain.order.repository;

import e3i2.ecommerce_backoffice.domain.order.entity.Ordering;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderingRepository extends JpaRepository<Ordering, Long> {
    @Query("""
        SELECT o
        FROM Ordering o
        JOIN FETCH o.customer c
        JOIN FETCH o.product p
        JOIN FETCH o.admin a
        WHERE (:search IS NULL OR
               (LOWER(o.orderNo) LIKE CONCAT('%', LOWER(:search), '%') OR
                LOWER(c.customerName) LIKE CONCAT('%', LOWER(:search), '%')))
        AND (:orderStatus IS NULL OR o.orderStatus = :orderStatus)
        AND o.deleted = false
        """)
    Page<Ordering> findOrders(
            @Param("search") String search,
            @Param("orderStatus") OrderingStatus orderStatus,
            Pageable pageable
    );

    Optional<Ordering> findByOrderIdAndDeletedFalse(Long orderId);

    @Query("""
           SELECT count(o.orderId)
           FROM Ordering o
           WHERE FUNCTION('DATE', o.createdAt) = CURRENT_DATE
           AND o.deleted = false
           """)
    Long countByOrderQuantityLower5DeletedFalse();

    Long countByDeletedFalse();

    @Query("""
        SELECT COALESCE(SUM(o.orderTotalPrice), 0)
        FROM Ordering o
        WHERE o.deleted = false
    """)
    Long sumTotalSales();

    @Query("""
        SELECT COALESCE(SUM(o.orderTotalPrice), 0)
        FROM Ordering o
        WHERE o.deleted = false
          AND o.orderAt BETWEEN :start AND :end
    """)
    Long sumTodaySales(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    long countByOrderStatusAndDeletedFalse(OrderingStatus status);

    @Query("""
        select o
        from Ordering o
        join fetch o.customer c
        join fetch o.product p
        order by o.createdAt desc
    """)
    List<Ordering> findRecentOrders(Pageable pageable);
}
