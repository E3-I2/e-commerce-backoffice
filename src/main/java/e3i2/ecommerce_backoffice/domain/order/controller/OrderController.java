package e3i2.ecommerce_backoffice.domain.order.controller;

import e3i2.ecommerce_backoffice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/order")
public class OrderController {
    private final OrderService orderService;

}
