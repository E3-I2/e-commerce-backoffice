package e3i2.ecommerce_backoffice.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeniedAdminRequest {
    @NotBlank(message = "거부 사유는 필수값입니다")
    private String deniedReason;
}
