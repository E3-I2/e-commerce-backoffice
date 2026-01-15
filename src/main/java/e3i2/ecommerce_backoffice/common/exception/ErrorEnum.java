package e3i2.ecommerce_backoffice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_ADMIN_STATUS_NOT_WAIT;
import static e3i2.ecommerce_backoffice.common.util.Constants.MSG_LOGOUT_DUPLICATED;

@Getter
public enum ErrorEnum {
    NOT_ADMIN_STATUS_WAIT(HttpStatus.CONFLICT, MSG_ADMIN_STATUS_NOT_WAIT)
    , LOGOUT_DUPLICATED(HttpStatus.UNAUTHORIZED, MSG_LOGOUT_DUPLICATED);

    private final HttpStatus status;
    private final String message;

    ErrorEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;

    }
}
