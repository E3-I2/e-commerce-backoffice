package e3i2.ecommerce_backoffice.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_ADMIN_ACCOUNT_DENY_BLANK_ERR;

@Getter
public class DeniedAdminRequest {
    @NotBlank(message = MSG_ADMIN_ACCOUNT_DENY_BLANK_ERR)
    private String deniedReason;
}
