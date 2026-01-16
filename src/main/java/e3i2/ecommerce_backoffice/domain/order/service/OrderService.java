package e3i2.ecommerce_backoffice.domain.order.service;

import e3i2.ecommerce_backoffice.common.exception.ServiceErrorException;
import e3i2.ecommerce_backoffice.common.util.pagination.ItemsWithPagination;
import e3i2.ecommerce_backoffice.domain.order.dto.SearchOrderResponse;
import e3i2.ecommerce_backoffice.domain.order.entity.Ordering;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import e3i2.ecommerce_backoffice.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static e3i2.ecommerce_backoffice.common.exception.ErrorEnum.ERR_NOT_FOUND_ORDER;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    // 주문 리스트 통합 조회
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ItemsWithPagination<List<SearchOrderResponse>> searchAllOrder(
            String orderNo, String customerName, OrderingStatus orderStatus, Integer page, Integer limit, String sortBy, String sortOrder) {
        Page<Ordering> orders = orderRepository.findOrders(
                orderNo,
                customerName,
                orderStatus,
                PageRequest.of(page - 1,
                        limit,
                        Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy)
                )
        );

        List<SearchOrderResponse> items = orders.stream().map(order -> SearchOrderResponse.register(
                order.getOrderId(),
                order.getOrderNo(),
                order.getCustomer().getCustomerId(),
                order.getCustomer().getCustomerName(),
                order.getProduct().getProductId(),
                order.getProduct().getProductName(),
                order.getOrderQuantity(),
                order.getOrderTotalPrice(),
                order.getOrderAt(),
                order.getOrderStatus(),
                order.getAdmin().getAdminId(),
                order.getAdmin().getAdminName(),
                order.getAdmin().getRole()
        )).toList();

        return ItemsWithPagination.register(items, page, limit, orders.getTotalElements());
    }

    // 주문 상세 조회
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SearchOrderResponse searchOrder(Long orderId) {
        Ordering order = orderRepository.findByOrderIdAndDeletedFalse(orderId).
                orElseThrow(() -> new ServiceErrorException(ERR_NOT_FOUND_ORDER));
        return SearchOrderResponse.register(
                order.getOrderId(),
                order.getOrderNo(),
                order.getCustomer().getCustomerId(),
                order.getCustomer().getCustomerName(),
                order.getProduct().getProductId(),
                order.getProduct().getProductName(),
                order.getOrderQuantity(),
                order.getOrderTotalPrice(),
                order.getOrderAt(),
                order.getOrderStatus(),
                order.getAdmin().getAdminId(),
                order.getAdmin().getAdminName(),
                order.getAdmin().getRole()
        );
    }
}
