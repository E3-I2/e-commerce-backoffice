package e3i2.ecommerce_backoffice.domain.admin.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_ADMIN_ACCOUNT_DENY_SIZE_ERR;

@Getter
public class AdminDenyRequest {
    @Size(min = 1, max = 50, message = MSG_ADMIN_ACCOUNT_DENY_SIZE_ERR)
    private String reason;
}
