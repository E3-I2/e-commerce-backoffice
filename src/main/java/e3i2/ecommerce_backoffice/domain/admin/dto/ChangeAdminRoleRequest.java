package e3i2.ecommerce_backoffice.domain.admin.dto;

import e3i2.ecommerce_backoffice.domain.admin.entity.AdminRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_ADMIN_ACCOUNT_ROLE_BLANK_ERR;

@Getter
public class ChangeAdminRoleRequest {
    @NotBlank(message = MSG_ADMIN_ACCOUNT_ROLE_BLANK_ERR)
    private AdminRole role;
}
