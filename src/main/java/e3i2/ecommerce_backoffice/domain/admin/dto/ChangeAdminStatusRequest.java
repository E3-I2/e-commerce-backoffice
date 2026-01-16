package e3i2.ecommerce_backoffice.domain.admin.dto;

import e3i2.ecommerce_backoffice.domain.admin.entity.AdminStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_ADMIN_ACCOUNT_STATUS_BLANK_ERR;

@Getter
public class ChangeAdminStatusRequest {
    @NotBlank(message = MSG_ADMIN_ACCOUNT_STATUS_BLANK_ERR)
    private AdminStatus status;
}