package e3i2.ecommerce_backoffice.domain.order.service;

import e3i2.ecommerce_backoffice.common.dto.session.SessionAdmin;
import e3i2.ecommerce_backoffice.common.exception.ServiceErrorException;
import e3i2.ecommerce_backoffice.domain.order.dto.ChangeOrderingStatusRequest;
import e3i2.ecommerce_backoffice.domain.order.dto.ChangeOrderingStatusResponse;
import e3i2.ecommerce_backoffice.domain.order.entity.Ordering;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import e3i2.ecommerce_backoffice.domain.order.repository.OrderingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static e3i2.ecommerce_backoffice.common.exception.ErrorEnum.ERR_NOT_FOUND_ORDER;

@Service
@RequiredArgsConstructor
public class OrderingService {
    private final OrderingRepository orderingRepository;

    @Transactional
    public ChangeOrderingStatusResponse updateStatusOrder(Long orderId, ChangeOrderingStatusRequest request, SessionAdmin sessionAdmin) {
        Ordering ordering = orderingRepository.findById(orderId).orElseThrow(
                () -> new ServiceErrorException(ERR_NOT_FOUND_ORDER)
        );

        OrderingStatus current = ordering.getOrderStatus();
        OrderingStatus next = request.getStatus();


        //배송완료 상태는 변경 불가
        if (current == OrderingStatus.DELIVERED) {
            throw new IllegalStateException("배송완료된 주문은 상태를 변경할 수 없습니다");
        }

        //상태 전이 검증
        if (!isValidNextStatus(current, next)) {
            throw new IllegalStateException("주문 상태는 ~~~ 순으로만 변경할 수 있습니다");
        }
        

        //상태 변경
        ordering.changeStatus(next);

        return ChangeOrderingStatusResponse.register(
                ordering.getOrderId(),
                ordering.getOrderNo(),
                ordering.getCustomer().getCustomerId(),
                ordering.getCustomer().getCustomerName(),
                ordering.getCustomer().getCustomerName(),
                ordering.getProduct().getProductId(),
                ordering.getProduct().getProductName(),
                ordering.getOrderQuantity(),
                ordering.getOrderTotalPrice(),
                ordering.getCreatedAt(),
                ordering.getOrderStatus()
        );
    }

    //상태 전이 검증 메서드(준비중 -> 배송중 -> 배송완료 순으로만 진행되도록)
    private boolean isValidNextStatus(OrderingStatus current, OrderingStatus next) {
        return switch (current) {
            case PREPARING -> next == OrderingStatus.SHIPPING;
            case SHIPPING -> next == OrderingStatus.DELIVERED;
            default -> false;
        };
    }
}
