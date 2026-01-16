package e3i2.ecommerce_backoffice.domain.order.repository;

import e3i2.ecommerce_backoffice.domain.order.entity.Ordering;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Ordering, Long> {
    @Query("""
        SELECT o
        FROM Ordering o
        WHERE (LOWER(o.orderNo) LIKE CONCAT('%', LOWER(:orderNo), '%') OR :orderNo IS NULL)
        AND (LOWER(o.customer.customerName) LIKE CONCAT('%', LOWER(:customerName), '%') OR :customerName IS NULL)
        AND (o.orderStatus = :orderStatus OR :orderStatus IS NULL)\
        AND (o.deleted = false)
        """)

    Page<Ordering> findOrders(
            @Param("orderNo") String orderNo,
            @Param("customerName") String customerName,
            @Param("orderStatus") OrderingStatus orderStatus,
            Pageable pageable
    );

    Optional<Ordering> findByOrderIdAndDeletedFalse(Long orderId);
}
