package e3i2.ecommerce_backoffice.domain.dashboard.service;

import e3i2.ecommerce_backoffice.domain.customer.entity.Customer;
import e3i2.ecommerce_backoffice.domain.dashboard.dto.SearchRecentListResponse;
import e3i2.ecommerce_backoffice.domain.dashboard.dto.SearchWidgetsResponse;
import e3i2.ecommerce_backoffice.domain.order.entity.Ordering;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import e3i2.ecommerce_backoffice.domain.order.repository.OrderingRepository;
import e3i2.ecommerce_backoffice.domain.product.entity.Product;
import e3i2.ecommerce_backoffice.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final OrderingRepository orderingRepository;
    private final ProductRepository productRepository;

    @Transactional
    public SearchWidgetsResponse getWidgets() {
        // 오늘 날짜 범위
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        // 매출
        Long totalSales = orderingRepository.sumTotalSales();
        Long todaySales = orderingRepository.sumTodaySales(startOfDay, endOfDay);

        // 주문 상태별 수
        long preparingCount = orderingRepository.countByOrderStatusAndDeletedFalse(OrderingStatus.PREPARING);
        long shippingCount = orderingRepository.countByOrderStatusAndDeletedFalse(OrderingStatus.SHIPPING);
        long deliveredCount = orderingRepository.countByOrderStatusAndDeletedFalse(OrderingStatus.DELIVERED);

        // 재고
        long lowStockCount = productRepository.countLowStockProducts();
        long soldOutStockCount = productRepository.countByQuantityAndDeletedFalse(0L);

        return SearchWidgetsResponse.register(
                totalSales,
                todaySales,
                preparingCount,
                shippingCount,
                deliveredCount,
                lowStockCount,
                soldOutStockCount
        );
    }

    @Transactional
    public List<SearchRecentListResponse> getRecentList() {

        List<Ordering> orders = orderingRepository.findRecentOrders(
                PageRequest.of(0, 10)
        );

        List<SearchRecentListResponse> responses = new ArrayList<>();

        for (Ordering order : orders) {
            Customer customer = order.getCustomer();
            Product product = order.getProduct();
            SearchRecentListResponse response = SearchRecentListResponse.register(
                    order.getOrderId(),
                    order.getOrderNo(),
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getEmail(),
                    product.getProductName(),
                    order.getOrderQuantity(),
                    order.getOrderTotalPrice(),
                    order.getCreatedAt(),
                    order.getOrderStatus()
            );
            responses.add(response);
        }

        return responses;
    }

}
