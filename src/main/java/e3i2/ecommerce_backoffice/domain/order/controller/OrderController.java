package e3i2.ecommerce_backoffice.domain.order.controller;

import e3i2.ecommerce_backoffice.common.annotation.LoginSessionCheck;
import e3i2.ecommerce_backoffice.common.dto.response.DataResponse;
import e3i2.ecommerce_backoffice.common.util.pagination.ItemsWithPagination;
import e3i2.ecommerce_backoffice.domain.order.dto.GetAllOrderResponse;
import e3i2.ecommerce_backoffice.domain.order.entity.OrderingStatus;
import e3i2.ecommerce_backoffice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/order")
public class OrderController {
    private final OrderService orderService;

    // 주문 리스트 통합 조회
    @GetMapping()
    @LoginSessionCheck
    public ResponseEntity<DataResponse<ItemsWithPagination<List<GetAllOrderResponse>>>> getAllOrderResponse(
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) OrderingStatus orderStatus,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "orderAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder

    ) {
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.success(HttpStatus.OK.name(),
                orderService.getAllOrder(orderNo, customerName, orderStatus, page, limit, sortBy, sortOrder)));
    }

}
