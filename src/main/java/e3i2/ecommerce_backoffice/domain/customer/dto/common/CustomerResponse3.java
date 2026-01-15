package e3i2.ecommerce_backoffice.domain.customer.dto.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonPropertyOrder({"success", "code", "message", "data"})
public class CustomerResponse3<T> {
    public Boolean success;
    public String code;
    public String message;
    public T data;

    private static <T> CustomerResponse3 <T> regist(Boolean success, String code, String message, T data) {
        CustomerResponse3<T> response = new CustomerResponse3<>();
        response.success = success;
        response.code = code;
        response.message = message;
        response.data = data;

        return response;
    }

    public static <T> CustomerResponse3<T> ok(T data) {return regist(true, HttpStatus.OK.name(), "고객 상태가 업데이트되었습니다", data);}
}

