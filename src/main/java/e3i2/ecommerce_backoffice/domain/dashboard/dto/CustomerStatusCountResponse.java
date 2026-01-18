package e3i2.ecommerce_backoffice.domain.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
@JsonPropertyOrder({
        "status"
        , "count"
})
public class CustomerStatusCountResponse {
    private final CustomerStatus status;
    private final Long count;

    private CustomerStatusCountResponse(CustomerStatus status, Long count) {
        this.status = status;
        this.count = count;
    }

    public static CustomerStatusCountResponse register(CustomerStatus status, Long count) {
        return new CustomerStatusCountResponse(status, count);
    }
}
