package e3i2.ecommerce_backoffice.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChangeOrderingStatusResponse {
    private Long orderId;
    private String orderNo;
    private Long customerId;
    private String customerName;
    private String email;
    private Long productId;
    private String productName;
    private Long orderQuantity;
    private Long orderTotalPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private OrderingStatus status;

    public static ChangeOrderingStatusResponse register(
        Long orderId,
        String orderNo,
        Long customerId,
        String customerName,
        String email,
        Long productId,
        String productName,
        Long orderQuantity,
        Long orderTotalPrice,
        LocalDateTime createdAt,
        OrderingStatus status
    ) {
        ChangeOrderingStatusResponse response = new ChangeOrderingStatusResponse();
        response.orderId = orderId;
        response.orderNo = orderNo;
        response.customerId = customerId;
        response.customerName = customerName;
        response.email = email;
        response.productId = productId;
        response.productName = productName;
        response.orderQuantity = orderQuantity;
        response.orderTotalPrice = orderTotalPrice;
        response.createdAt = createdAt;
        response.status= status;
        return response;
    }

}
