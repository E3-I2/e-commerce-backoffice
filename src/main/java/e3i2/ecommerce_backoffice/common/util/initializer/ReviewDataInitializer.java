package e3i2.ecommerce_backoffice.common.util.initializer;

import e3i2.ecommerce_backoffice.domain.review.entity.Review;
import e3i2.ecommerce_backoffice.domain.review.repository.ReviewRepositorty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.hibernate.query.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Component
@Profile("local")
@RequiredArgsConstructor
public class ReviewDataInitializer implements ApplicationRunner {

    private final ReviewRepositorty reviewRepository;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        if(reviewRepository.count() > 0) {
            return;
        }

        Long[] rating = ThreadLocalRandom.current().longs(20, 1, 5).boxed().toArray(Long[]::new);
        IntStream.rangeClosed(1, 20).mapToObj(index -> Review.register(
                switch (index % 3) {
                    case 0 -> "Good";
                    case 1 -> "Bad";
                    case 2 -> "Soso";
                    default -> "Good";
                },
                rating[index - 1],


        ))
    }
}
