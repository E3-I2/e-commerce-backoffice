package e3i2.ecommerce_backoffice.domain.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.*;

@Getter
public class CreateOrderingRequest {
    @NotNull(message = MSG_ORDER_CUSTOMER_ID_NULL_ERR)
    private Long customerId;
    @NotNull(message = MSG_ORDER_PRODUCT_ID_NULL_ERR)
    private Long productId;
    @NotNull(message = MSG_ORDER_QUANTITY_NULL_ERR)
    @Min(value = 1, message = MSG_ORDER_QUANTITY_MIN_ERR)
    @Max(value = Long.MAX_VALUE, message = MSG_ORDER_QUANTITY_MAX_ERR)
    private Long orderQuantity;
}
