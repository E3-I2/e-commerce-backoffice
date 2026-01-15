package e3i2.ecommerce_backoffice.domain.customer.dto;

import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PutStatusCustomerRequest {
    @NotBlank(message = "고객 상태는 필수값입니다")
    private CustomerStatus status;
}
