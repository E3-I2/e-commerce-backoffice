package e3i2.ecommerce_backoffice.domain.dashboard.service;

import e3i2.ecommerce_backoffice.domain.admin.entity.AdminStatus;
import e3i2.ecommerce_backoffice.domain.admin.repository.AdminRepository;
import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import e3i2.ecommerce_backoffice.domain.customer.repository.CustomerRepository;
import e3i2.ecommerce_backoffice.domain.dashboard.dto.*;
import e3i2.ecommerce_backoffice.domain.order.repository.OrderingRepository;
import e3i2.ecommerce_backoffice.domain.product.repository.ProductRepository;
import e3i2.ecommerce_backoffice.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AdminRepository adminRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderingRepository orderingRepository;
    private final ReviewRepository reviewRepository;

    public StatResponse getStat() {
        SummaryResponse summaryResponse = SummaryResponse.register(
                adminRepository.countByDeletedFalse()
                , adminRepository.countByAdminStatusDeletedFalse(AdminStatus.ACT)
                , customerRepository.countByDeletedFalse()
                , customerRepository.countByCustomerStatusDeletedFalse(CustomerStatus.ACT)
                , productRepository.countByDeletedFalse()
                , productRepository.countByProductQuantityLower5DeletedFalse()
                , orderingRepository.countByDeletedFalse()
                , orderingRepository.countByOrderQuantityLower5DeletedFalse()
                , reviewRepository.countByDeletedFalse()
                , reviewRepository.findAverageRating()
        );

        ChartsResponse chartsResponse = ChartsResponse.register(
                reviewRepository.countByRatingGroupByRating()
                        .stream()
                        .map(reviewRatingCount ->
                                ReviewRatingCountResponse.register(
                                        reviewRatingCount.getRating(),
                                        reviewRatingCount.getCount()
                                )
                        ).toList()
                , customerRepository.countByStatusGroupByCustomerStatus()
                        .stream()
                        .map(customerStatusCount ->
                                CustomerStatusCountResponse.register(
                                        customerStatusCount.getStatus(),
                                        customerStatusCount.getCount()
                                )
                        ).toList()
                , productRepository.countByCategoryGroupByCategory()
                        .stream()
                        .map(productCategoryCount ->
                                ProductCategoryCountResponse.register(
                                        productCategoryCount.getCategory(),
                                        productCategoryCount.getCount()
                                )
                        ).toList()
        );

        return StatResponse.register(summaryResponse, chartsResponse);
    }
}
