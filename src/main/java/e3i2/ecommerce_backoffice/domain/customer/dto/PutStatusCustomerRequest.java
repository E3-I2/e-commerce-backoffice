package e3i2.ecommerce_backoffice.domain.customer.dto;

import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PutStatusCustomerRequest {
    @NotNull
    private CustomerStatus status;
}
