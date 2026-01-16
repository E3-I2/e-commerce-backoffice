package e3i2.ecommerce_backoffice.domain.order.service;

import e3i2.ecommerce_backoffice.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
}
