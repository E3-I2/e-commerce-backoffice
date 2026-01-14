package e3i2.ecommerce_backoffice.domain.admin.dto;

import e3i2.ecommerce_backoffice.domain.admin.entity.AdminRole;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {
    private String adminName;
    private String email;
    private String password;
    private String phone;
    private AdminRole role;
}
