package e3i2.ecommerce_backoffice.domain.admin.dto.common;

import e3i2.ecommerce_backoffice.domain.admin.dto.SearchAdminDetailResponse;
import e3i2.ecommerce_backoffice.domain.admin.dto.common.Pagination;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminListDataResponse {
    private List<SearchAdminDetailResponse> items;
    private Pagination pagination;

    public static AdminListDataResponse regist(List<SearchAdminDetailResponse> items, Pagination pagination) {
        AdminListDataResponse response = new AdminListDataResponse();
        response.items = items;
        response.pagination = pagination;
        return response;
    }
}
