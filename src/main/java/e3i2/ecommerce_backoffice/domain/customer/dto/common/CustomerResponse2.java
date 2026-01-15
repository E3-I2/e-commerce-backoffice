package e3i2.ecommerce_backoffice.domain.customer.dto.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomerResponse2 {
    public Boolean success;
    public String code;
    public String message;

    private static CustomerResponse2 regist(Boolean success, String code, String message) {
        CustomerResponse2 response = new CustomerResponse2();
        response.success = success;
        response.code = code;
        response.message = message;

        return response;
    }

    public static CustomerResponse2 deleted() {return regist(true, HttpStatus.OK.name(), "고객이 삭제되었습니다");}
}
