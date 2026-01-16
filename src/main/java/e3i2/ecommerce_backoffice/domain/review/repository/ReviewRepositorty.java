package e3i2.ecommerce_backoffice.domain.review.repository;

import e3i2.ecommerce_backoffice.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepositorty extends JpaRepository<Review, Long> {
}
