package e3i2.ecommerce_backoffice.domain.admin.dto;

import e3i2.ecommerce_backoffice.domain.admin.entity.AdminRole;
import e3i2.ecommerce_backoffice.domain.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupResponse {
    private final Long adminId;
    private final String adminName;
    private final String email;
    private final String phone;
    private final AdminRole role;
    private final LocalDateTime createdAt;
    private final AdminStatus status;

    public SignupResponse(Long adminId, String adminName, String email, String phone, AdminRole role, LocalDateTime createdAt, AdminStatus status) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.createdAt = createdAt;
        this.status = status;
    }
}
