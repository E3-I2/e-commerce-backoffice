package e3i2.ecommerce_backoffice.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchWidgetsResponse {
    // 매출
    private Long totalSales;
    private Long todaySales;

    // 주문 상태
    private Long preparingCount;
    private Long shippingCount;
    private Long deliveredCount;

    // 재고
    private Long lowStockCount;
    private Long soldOutStockCount;
}
