package e3i2.ecommerce_backoffice.domain.customer.service;

import e3i2.ecommerce_backoffice.domain.customer.dto.*;
import e3i2.ecommerce_backoffice.domain.customer.dto.common.CustomerResponse;
import e3i2.ecommerce_backoffice.domain.customer.dto.common.CustomerResponse2;
import e3i2.ecommerce_backoffice.domain.customer.dto.common.CustomerWithPagination;
import e3i2.ecommerce_backoffice.domain.customer.entity.Customer;
import e3i2.ecommerce_backoffice.domain.customer.entity.CustomerStatus;
import e3i2.ecommerce_backoffice.domain.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    // 고객 리스트 조회
    @Transactional(readOnly = true)
    public CustomerWithPagination findAll(String customerName, String email, Integer page, Integer limit, String sortBy, String sortOrder, CustomerStatus status) {
        List<GetCustomerResponse> customers = customerRepository.findAllByFilters(
                customerName, email, status, PageRequest.of(page - 1, limit, Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy))
        ).stream().map(customer -> new GetCustomerResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerStatus().getStatusDescription(),
                customer.getCreatedAt()
        )).toList();

        return CustomerWithPagination.regist(customers, page, limit, (long) customers.size());
    }

    // 고객 상세 조회
    @Transactional(readOnly = true)
    public GetCustomerResponse findOne(Long customerId) {
        Customer customer = customerRepository.findByCustomerIdAndDeletedFalse(customerId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 고객입니다")
        );
        return new GetCustomerResponse(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail(), customer.getPhone(), customer.getCustomerStatus().getStatusDescription(), customer.getCreatedAt());
    }

    // 고객 정보 수정
    @Transactional
    public PutInfoCustomerResponse updateInfo(Long customerId, @Valid PutInfoCustomerRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 고객입니다")
        );
        boolean emailExists = customerRepository.existsByEmail(request.getEmail());
        if (emailExists) {
            throw new IllegalArgumentException("사용 불가능한 이메일입니다");
        }
        boolean phoneExists = customerRepository.existsByPhone(request.getPhone());
        if (phoneExists) {
            throw new IllegalArgumentException("사용 불가능한 전화번호입니다");
        }
        customer.update(request.getCustomerName(), request.getEmail(), request.getPhone());
        return new PutInfoCustomerResponse(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail(), customer.getPhone(), customer.getCustomerStatus().getStatusDescription(), customer.getCreatedAt());

    }

    // 고객 상태 변경
    @Transactional
    public PutStatusCustomerResponse updateStatus(Long customerId, @Valid PutStatusCustomerRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 고객입니다")
        );
        customer.statusChange(request.getStatus());
        return new PutStatusCustomerResponse(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail(), customer.getPhone(), customer.getCustomerStatus().getStatusDescription(), customer.getCreatedAt());
    }

    // 고객 삭제
    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 고객입니다")
        );
        customer.delete();
    }
}
