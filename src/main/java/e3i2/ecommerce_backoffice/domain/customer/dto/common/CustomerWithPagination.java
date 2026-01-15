package e3i2.ecommerce_backoffice.domain.customer.dto.common;

import e3i2.ecommerce_backoffice.domain.customer.dto.GetCustomerResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerWithPagination {
    private List<GetCustomerResponse> customers;
    private Pagination pagination;

    public static CustomerWithPagination regist(List<GetCustomerResponse> customers, Integer page, Integer limit, Long total) {
        CustomerWithPagination customerWithPagination = new CustomerWithPagination();
        customerWithPagination.customers = customers;
        customerWithPagination.pagination = Pagination.regist(page, limit, total, (int) Math.ceil((double) total / limit));

        return customerWithPagination;
    }
}
