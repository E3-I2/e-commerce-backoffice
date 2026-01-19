package e3i2.ecommerce_backoffice.domain.admin.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_PASSWORD_SIZE_ERR;

@Getter
public class ChangeMyPasswordRequest {
    @Size(min = 8, max = 20, message = MSG_PASSWORD_SIZE_ERR)
    private String currentPassword;
    @Size(min = 8, max = 20, message = MSG_PASSWORD_SIZE_ERR)
    private String newPassword;
}
