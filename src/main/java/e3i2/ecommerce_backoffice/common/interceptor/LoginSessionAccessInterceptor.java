package e3i2.ecommerce_backoffice.common.interceptor;

import e3i2.ecommerce_backoffice.common.annotation.LoginSessionCheck;
import e3i2.ecommerce_backoffice.common.exception.ServiceErrorException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static e3i2.ecommerce_backoffice.common.exception.ErrorEnum.ERR_NOT_LOGIN_ACCESS;
import static e3i2.ecommerce_backoffice.common.util.Constants.ADMIN_SESSION_NAME;

// TODO Stage 반영시 주석 삭제
@Component
public class LoginSessionAccessInterceptor implements HandlerInterceptor {
    // 요청이 컨트롤러 접근 전에 체크 (preHandle)
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 실행될 메서드(handler) 가 현재 요청 하는 컨트롤러 메서드(HandlerMethod) 인지 확인
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginSessionCheck loginSessionCheck = handlerMethod.getMethodAnnotation(LoginSessionCheck.class);

            // 어노테이션 없으면 통과
            if (loginSessionCheck == null) {
                return true;
            }

            // 어노테이션이 있으면 세션 체크
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(ADMIN_SESSION_NAME) == null) {
                throw new ServiceErrorException(ERR_NOT_LOGIN_ACCESS);
            }

            // 세션 체크 후 통과
            return true;
        }

        // 인터셉터 영향 받지 않는 모든 것들은 통과
        return true;
    }
}
