package e3i2.ecommerce_backoffice.domain.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static e3i2.ecommerce_backoffice.common.util.Constants.*;

@Getter
public class UpdateMyProfileRequest {
    @NotBlank(message = MSG_NAME_BLANK_ERR)
    private String adminName;
    @Email(message = MSG_EMAIL_PATTERN_ERR)
    private String email;
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = MSG_PHONE_PATTERN_ERR
    )
    private String phone;
}
