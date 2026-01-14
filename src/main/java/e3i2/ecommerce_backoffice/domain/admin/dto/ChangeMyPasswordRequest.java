package e3i2.ecommerce_backoffice.domain.admin.dto;

import lombok.Getter;

@Getter
public class ChangeMyPasswordRequest {
    private String currentPassword;
    private String newPassword;
}
