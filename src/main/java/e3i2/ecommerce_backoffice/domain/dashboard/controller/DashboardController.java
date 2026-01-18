package e3i2.ecommerce_backoffice.domain.dashboard.controller;

import e3i2.ecommerce_backoffice.common.annotation.LoginSessionCheck;
import e3i2.ecommerce_backoffice.common.dto.response.DataResponse;
import e3i2.ecommerce_backoffice.domain.dashboard.dto.SearchRecentListResponse;
import e3i2.ecommerce_backoffice.domain.dashboard.dto.SearchWidgetsResponse;
import e3i2.ecommerce_backoffice.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    //위젯 데이터
    @GetMapping("/widgets")
    @LoginSessionCheck
    public ResponseEntity<DataResponse<SearchWidgetsResponse>> getWidgets() {
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.success(HttpStatus.OK.name(),
                dashboardService.getWidgets()));
    }

    //최근 주문 목록
    @GetMapping("/recent")
    @LoginSessionCheck
    public ResponseEntity<DataResponse<List<SearchRecentListResponse>>> getRecentOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.success(HttpStatus.OK.name(),
                dashboardService.getRecentList()));
    }
}
