package e3i2.ecommerce_backoffice.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import e3i2.ecommerce_backoffice.domain.admin.entity.AdminRole;
import e3i2.ecommerce_backoffice.domain.admin.entity.AdminStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 관리자 상세 조회
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "phone",
        "role",
        "status",
        "createdAt",
        "acceptedAt",
        "deniedAt",
        "requestMessage",
        "deniedReason"
})
public class SearchAdminDetailResponse {

    @JsonProperty("id")
    private Long adminId;

    @JsonProperty("name")
    private String adminName;

    private String email;
    private String phone;
    private AdminRole role;
    private AdminStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String acceptedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String deniedAt;

    // 관리자 상태별 조건부 필드
    private String requestMessage;  // WAIT 상태일 때만
    private String deniedReason; // DENY 상태일 때만

    public static SearchAdminDetailResponse register(
            Long adminId,
            String adminName,
            String email,
            String phone,
            AdminRole role,
            AdminStatus status,
            LocalDateTime createdAt,
            String acceptedAt,
            String deniedAt,
            String requestMessage,
            String deniedReason
    ) {
        SearchAdminDetailResponse response = new SearchAdminDetailResponse();

        response.adminId = adminId;
        response.adminName = adminName;
        response.email = email;
        response.phone = phone;
        response.role = role;
        response.status = status;
        response.createdAt = createdAt;
        response.acceptedAt = acceptedAt;
        response.deniedAt = deniedAt;

        // 상태별 조건부 필드
        if (status == AdminStatus.WAIT) {
            response.requestMessage = requestMessage;
        } else if (status == AdminStatus.DENY) {
            response.deniedReason = deniedReason;
        }

        return response;
    }
}
