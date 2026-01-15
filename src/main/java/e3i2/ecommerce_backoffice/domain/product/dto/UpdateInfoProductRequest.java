package e3i2.ecommerce_backoffice.domain.product.dto;

import e3i2.ecommerce_backoffice.domain.product.entity.ProductCategory;
import e3i2.ecommerce_backoffice.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static e3i2.ecommerce_backoffice.common.util.Constants.*;

@Getter
public class UpdateInfoProductRequest {
    @NotBlank(message = MSG_PRODUCT_NAME_BLANK_ERR)
    private String productName;
    @NotBlank(message = MSG_PRODUCT_CATEGORY_BLANK_ERR)
    private ProductCategory category;
    @NotBlank(message = MSG_PRODUCT_PRICE_BLANK_ERR)
    @Min(value = 0, message = MSG_PRODUCT_PRICE_MINUS_ERR)
    @Max(value = Long.MAX_VALUE, message = MSG_PRODUCT_PRICE_MAX_ERR)
    private Long price;
}
