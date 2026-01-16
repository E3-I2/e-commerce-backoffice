package e3i2.ecommerce_backoffice.domain.product.dto;

import e3i2.ecommerce_backoffice.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_PRODUCT_STATUS_BLANK_ERR;

@Getter
public class UpdateStatusProductRequest {
    @NotBlank(message = MSG_PRODUCT_STATUS_BLANK_ERR)
    private ProductStatus status;
}
