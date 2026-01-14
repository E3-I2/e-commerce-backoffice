package e3i2.ecommerce_backoffice.domain.customer.controller;

import e3i2.ecommerce_backoffice.domain.customer.dto.CustomerResponse;
import e3i2.ecommerce_backoffice.domain.customer.dto.GetCustomerResponse;
import e3i2.ecommerce_backoffice.domain.customer.dto.PatchInfoCustomerRequest;
import e3i2.ecommerce_backoffice.domain.customer.dto.PatchStatusCustomerRequest;
import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import e3i2.ecommerce_backoffice.domain.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/api/admins/customers")
    public ResponseEntity<CustomerResponse<Page<GetCustomerResponse>>> getAll(
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false)CustomerStatus status
            ) {
        return ResponseEntity.ok(customerService.findAll(customerName, email, page, size, sortBy, direction, status));
    }

    @GetMapping("/api/admins/customers/{customerId}")
    public ResponseEntity<CustomerResponse<GetCustomerResponse>> getOne(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.findOne(customerId));
    }

    @PatchMapping("/api/admins/customers/{customerId}/info")
    public ResponseEntity<Void> patchInfo(@PathVariable Long customerId, @Valid @RequestBody PatchInfoCustomerRequest request) {
        customerService.updateInfo(customerId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/api/admins/customers/{customerId}/status")
    public ResponseEntity<Void> patchStatus(@PathVariable Long customerId, @Valid @RequestBody PatchStatusCustomerRequest request) {
        customerService.updateStatus(customerId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/api/admins/customers/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
