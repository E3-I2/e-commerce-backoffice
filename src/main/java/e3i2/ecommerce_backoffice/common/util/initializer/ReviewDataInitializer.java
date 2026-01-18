package e3i2.ecommerce_backoffice.common.util.initializer;

import e3i2.ecommerce_backoffice.domain.customer.entity.Customer;
import e3i2.ecommerce_backoffice.domain.customer.repository.CustomerRepository;
import e3i2.ecommerce_backoffice.domain.order.entity.Ordering;
import e3i2.ecommerce_backoffice.domain.order.repository.OrderingRepository;
import e3i2.ecommerce_backoffice.domain.product.entity.Product;
import e3i2.ecommerce_backoffice.domain.product.repository.ProductRepository;
import e3i2.ecommerce_backoffice.domain.review.entity.Review;
import e3i2.ecommerce_backoffice.domain.review.repository.ReviewRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class ReviewDataInitializer {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderingRepository orderingRepository;

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @Scheduled(fixedDelay = 10000, initialDelay = 3000) // 3초 후 시작, 10초마다 체크
    @Transactional
    public void checkAndInitializeReviews() {
        // 이미 초기화되었으면 더 이상 체크하지 않음
        if (initialized.get()) {
            return;
        }

        // 리뷰 데이터가 이미 존재하면 초기화 완료로 간주
        if (reviewRepository.count() > 0) {
            log.info("리뷰 데이터가 이미 존재합니다. 초기화 작업을 중단합니다.");
            initialized.set(true);
            return;
        }

        // 필요한 데이터들 미리 조회
        List<Customer> customers = customerRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<Ordering> orders = orderingRepository.findAll();

        // 하나라도 없으면 대기
        if (customers.isEmpty() || products.isEmpty() || orders.isEmpty()) {
            log.info("리뷰 생성 대기 중 - Customer: {}, Product: {}, Ordering: {}",
                    customers.size(), products.size(), orders.size());
            return;
        }

        // 모든 데이터가 있으면 리뷰 생성 시작
        log.info("필수 데이터 확인 완료 - Customer: {}, Product: {}, Ordering: {}",
                customers.size(), products.size(), orders.size());

        String[] reviewContents = {
                "정말 만족스러운 제품입니다. 품질이 우수하고 배송도 빨랐어요.",
                "기대 이상이었습니다. 재구매 의사 100%입니다!",
                "가격 대비 성능이 뛰어납니다. 추천합니다.",
                "배송이 조금 늦었지만 제품은 마음에 듭니다.",
                "생각보다 품질이 좋지 않네요. 아쉽습니다.",
                "디자인이 예쁘고 실용적입니다. 잘 사용하고 있어요.",
                "가족 모두 만족하며 사용 중입니다.",
                "포장이 꼼꼼하고 제품 상태가 완벽했습니다.",
                "설명과 달라서 실망했습니다.",
                "가성비 최고! 다음에도 구매할게요.",
                "색상이 사진과 약간 다르지만 그래도 괜찮습니다.",
                "사용하기 편리하고 내구성도 좋은 것 같아요.",
                "선물용으로 샀는데 받는 사람이 너무 좋아했어요.",
                "품질은 좋은데 가격이 조금 비싼 것 같아요.",
                "배송 중 파손되어 교환했는데 응대가 좋았습니다.",
                "기대했던 것보다 크기가 작아서 아쉬워요.",
                "재질이 튼튼하고 디자인도 심플해서 좋습니다.",
                "처음 사용하는데 설명서가 부실해서 어려웠어요.",
                "가격 대비 훌륭한 제품입니다. 강력 추천!",
                "아이가 정말 좋아합니다. 만족스러운 구매였어요."
        };

        Random random = new Random();

        // 현재 고객, 상품, 주문 데이터를 가지고 20개의 리뷰 생성
        IntStream.range(0, 20)
                .forEach(index -> {
                    Customer customer = customers.get(index % customers.size());
                    Product product = products.get(random.nextInt(products.size()));
                    Ordering order = orders.get(index % orders.size());

                    int rating = random.nextInt(5) + 1; // 1~5
                    String content = reviewContents[index];

                    Review review = Review.register(content, rating, order, customer, product);
                    reviewRepository.save(review);
                });

        log.info("리뷰 데이터 20개 초기화 완료");
        initialized.set(true);

    }
}
