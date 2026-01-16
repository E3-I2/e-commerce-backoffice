package e3i2.ecommerce_backoffice.domain.customer.dto;

import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_CUSTOMER_ACCOUNT_STATUS_BLANK_ERR;

@Getter
public class PutStatusCustomerRequest {
    @NotBlank(message = MSG_CUSTOMER_ACCOUNT_STATUS_BLANK_ERR)
    private CustomerStatus status;
}
