package e3i2.ecommerce_backoffice.domain.customer.service;

import e3i2.ecommerce_backoffice.domain.customer.dto.CustomerResponse;
import e3i2.ecommerce_backoffice.domain.customer.dto.GetCustomerResponse;
import e3i2.ecommerce_backoffice.domain.customer.dto.PatchInfoCustomerRequest;
import e3i2.ecommerce_backoffice.domain.customer.dto.PatchStatusCustomerRequest;
import e3i2.ecommerce_backoffice.domain.customer.entity.Customer;
import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import e3i2.ecommerce_backoffice.domain.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public CustomerResponse<Page<GetCustomerResponse>> findAll(String customerName, String email, int page, int size, String sortBy, String direction, CustomerStatus status) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Customer> customers = customerRepository.findAllByFilters(customerName, email, status, pageable);
        Page<GetCustomerResponse> response = customers.map(customer -> new  GetCustomerResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerStatus().getStatusDescription(),
                customer.getCreatedAt()
        ));
        return CustomerResponse.regist(true, "200", response);
    }

    @Transactional(readOnly = true)
    public CustomerResponse<GetCustomerResponse> findOne(Long customerId) {
        Customer customer = customerRepository.findByCustomerIdAndDeletedFalse(customerId).orElseThrow(
                () -> new IllegalArgumentException("Customer with id " + customerId + " not found")
        );
        GetCustomerResponse response = new GetCustomerResponse(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail(), customer.getPhone(), customer.getCustomerStatus().getStatusDescription(), customer.getCreatedAt());
        return CustomerResponse.regist(true, "200", response);
    }

    @Transactional
    public void updateInfo(Long customerId, @Valid PatchInfoCustomerRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("Customer with id " + customerId + " not found")
        );
        boolean emailExists = customerRepository.existsByEmail(request.getEmail());
        if (emailExists) {
            throw new IllegalArgumentException("사용 불가능한 이메일입니다.");
        }
        boolean phoneExists = customerRepository.existsByPhone(request.getPhone());
        if (phoneExists) {
            throw new IllegalArgumentException("사용 불가능한 전화번호입니다.");
        }
        customer.update(request.getCustomerName(), request.getEmail(), request.getPhone());

    }

    @Transactional
    public void updateStatus(Long customerId, @Valid PatchStatusCustomerRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("Customer with id " + customerId + " not found")
        );
        customer.statusChange(request.getStatus());
    }

    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("Customer with id " + customerId + " not found")
        );
        customer.delete();
    }
}
