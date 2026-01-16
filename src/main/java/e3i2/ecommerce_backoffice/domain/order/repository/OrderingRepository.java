package e3i2.ecommerce_backoffice.domain.order.repository;

import e3i2.ecommerce_backoffice.domain.order.entity.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderingRepository extends JpaRepository<Ordering, Long> {
}
