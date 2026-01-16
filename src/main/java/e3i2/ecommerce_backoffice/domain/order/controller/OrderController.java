package e3i2.ecommerce_backoffice.domain.order.controller;

import e3i2.ecommerce_backoffice.common.annotation.LoginSessionCheck;
import e3i2.ecommerce_backoffice.common.dto.response.DataResponse;
import e3i2.ecommerce_backoffice.common.dto.session.SessionAdmin;
import e3i2.ecommerce_backoffice.domain.order.dto.CreateOrderingRequest;
import e3i2.ecommerce_backoffice.domain.order.dto.CreateOrderingResponse;
import e3i2.ecommerce_backoffice.domain.order.service.OrderingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static e3i2.ecommerce_backoffice.common.util.Constants.ADMIN_SESSION_NAME;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderingService orderingService;

    @PostMapping
    @LoginSessionCheck
    public ResponseEntity<DataResponse<CreateOrderingResponse>> createProduct(
            @Valid @RequestBody CreateOrderingRequest request,
            @SessionAttribute(ADMIN_SESSION_NAME) SessionAdmin sessionAdmin
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.success(HttpStatus.OK.name(), orderingService.createOrder(request, sessionAdmin)));
    }


}
