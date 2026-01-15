package e3i2.ecommerce_backoffice.domain.customer.controller;

import e3i2.ecommerce_backoffice.common.annotation.LoginSessionCheck;
import e3i2.ecommerce_backoffice.domain.customer.dto.*;
import e3i2.ecommerce_backoffice.domain.customer.dto.common.CustomerResponse;
import e3i2.ecommerce_backoffice.domain.customer.dto.common.CustomerResponse2;
import e3i2.ecommerce_backoffice.domain.customer.dto.common.CustomerResponse3;
import e3i2.ecommerce_backoffice.domain.customer.dto.common.CustomerWithPagination;
import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import e3i2.ecommerce_backoffice.domain.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    // 고객 리스트 조회
    @GetMapping("/api/admins/customers")
//    @LoginSessionCheck
    public ResponseEntity<CustomerResponse<CustomerWithPagination>> getAll(
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false)CustomerStatus status
            ) {
        return ResponseEntity.ok(CustomerResponse.ok(customerService.findAll(customerName, email, page, limit, sortBy, sortOrder, status)));
    }

    // 고객 상세 조회
    @GetMapping("/api/admins/customers/{customerId}")
//    @LoginSessionCheck
    public ResponseEntity<CustomerResponse<GetCustomerResponse>> getOne(@PathVariable Long customerId) {
        return ResponseEntity.ok(CustomerResponse.ok(customerService.findOne(customerId)));
    }

    // 고객 정보 수정
    @PutMapping("/api/admins/customers/{customerId}/info")
//    @LoginSessionCheck
    public ResponseEntity<CustomerResponse<PutInfoCustomerResponse>> putInfo(@PathVariable Long customerId, @Valid @RequestBody PutInfoCustomerRequest request) {
        return ResponseEntity.ok(CustomerResponse.ok(customerService.updateInfo(customerId, request)));
    }

    // 고객 상태 변경
    @PutMapping("/api/admins/customers/{customerId}/status")
//    @LoginSessionCheck
    public ResponseEntity<CustomerResponse3<PutStatusCustomerResponse>> putStatus(@PathVariable Long customerId, @Valid @RequestBody PutStatusCustomerRequest request) {
        return ResponseEntity.ok(CustomerResponse3.ok(customerService.updateStatus(customerId, request)));
    }

    // 고객 삭제
    @DeleteMapping("/api/admins/customers/{customerId}")
//    @LoginSessionCheck
    public ResponseEntity<CustomerResponse2> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.ok().body(CustomerResponse2.deleted());
    }
}
