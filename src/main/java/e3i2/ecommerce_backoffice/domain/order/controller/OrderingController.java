package e3i2.ecommerce_backoffice.domain.order.controller;

import e3i2.ecommerce_backoffice.common.annotation.LoginSessionCheck;
import e3i2.ecommerce_backoffice.common.dto.response.DataResponse;
import e3i2.ecommerce_backoffice.common.dto.session.SessionAdmin;
import e3i2.ecommerce_backoffice.domain.order.dto.ChangeOrderingStatusRequest;
import e3i2.ecommerce_backoffice.domain.order.dto.ChangeOrderingStatusResponse;
import e3i2.ecommerce_backoffice.domain.order.service.OrderingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static e3i2.ecommerce_backoffice.common.util.Constants.ADMIN_SESSION_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/orders")
public class OrderingController {
    private final OrderingService orderingService;

    //주문 상태 수정
    @PutMapping("/{orderId}/status")
    @LoginSessionCheck
    public ResponseEntity<DataResponse<ChangeOrderingStatusResponse>> changeOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody ChangeOrderingStatusRequest request,
            @SessionAttribute(value = ADMIN_SESSION_NAME) SessionAdmin sessionAdmin
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        DataResponse.success(
                                HttpStatus.OK.name(),
                                orderingService.updateStatusOrder(orderId, request, sessionAdmin)
                        )
                );
    }
}
