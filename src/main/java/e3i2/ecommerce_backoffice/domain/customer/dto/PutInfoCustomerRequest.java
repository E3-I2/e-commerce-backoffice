package e3i2.ecommerce_backoffice.domain.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PutInfoCustomerRequest {
    @NotBlank(message = "고객 이름은 필수값입니다")
    private String customerName;
    @Email(message = "유효하지 않은 이메일 형식입니다")
    private String email;
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    @NotBlank(message = "유효하지 않은 전화번호 형식입니다")
    private String phone;
}
