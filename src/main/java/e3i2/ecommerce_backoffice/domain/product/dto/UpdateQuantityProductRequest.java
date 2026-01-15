package e3i2.ecommerce_backoffice.domain.product.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_PRODUCT_QUANTITY_BLANK_ERR;
import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_PRODUCT_QUANTITY_MAX_ERR;

@Getter
public class UpdateQuantityProductRequest {
    @NotBlank(message = MSG_PRODUCT_QUANTITY_BLANK_ERR)
    @Max(value = Long.MAX_VALUE, message = MSG_PRODUCT_QUANTITY_MAX_ERR)
    private Long quantity;
}
