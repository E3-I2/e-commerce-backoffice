package e3i2.ecommerce_backoffice.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import e3i2.ecommerce_backoffice.domain.admin.entity.AdminRole;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({
        "id",
        "orderNo",
        "customerId",
        "customerName",
        "productId",
        "productName",
        "quantity",
        "amount",
        "orderAt",
        "status",
        "AdminId",
        "AdminName",
        "AdminRole"
})
public class SearchAllOrderResponse {
    private Long id;
    private String orderNo;
    private Long customerId;
    private String customerName;
    private Long productId;
    private String productName;
    private Long quantity;
    private Long amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime orderAt;
    private OrderingStatus orderStatus;
    private Long AdminId;
    private String AdminName;
    private AdminRole AdminRole;

    public static SearchAllOrderResponse register(
            Long id, String orderNo, Long customerId, String customerName, Long productId, String productName,
            Long quantity, Long amount, LocalDateTime orderAt, OrderingStatus orderStatus,
            Long AdminId, String AdminName, AdminRole AdminRole) {
        SearchAllOrderResponse response = new SearchAllOrderResponse();
        response.id = id;
        response.orderNo = orderNo;
        response.customerId = customerId;
        response.customerName = customerName;
        response.productId = productId;
        response.productName = productName;
        response.quantity = quantity;
        response.amount = amount;
        response.orderAt = orderAt;
        response.orderStatus = orderStatus;
        response.AdminId = AdminId;
        response.AdminName = AdminName;
        response.AdminRole = AdminRole;

        return response;
    }

}
