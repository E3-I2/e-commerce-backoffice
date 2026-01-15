package e3i2.ecommerce_backoffice.domain.admin.dto.common;

import lombok.Getter;

@Getter
public class AdminApiResponse_token<T> {
    private final boolean success;
    private final String code;
    private final T data;
    private final String token;

    private AdminApiResponse_token(boolean success, String code, T data, String token) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.token = token;
    }

    //추후 jwt 토큰 사용 시 Response 형식
    public static <T> AdminApiResponse_token<T> success(
            String code,
            T data,
            String token
    ) {
        return new AdminApiResponse_token<>(true, code, data, token);
    }
}

