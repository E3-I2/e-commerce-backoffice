package e3i2.ecommerce_backoffice.domain.review.service;

import e3i2.ecommerce_backoffice.domain.review.repository.ReviewRepositorty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepositorty reviewRepositorty;

}
