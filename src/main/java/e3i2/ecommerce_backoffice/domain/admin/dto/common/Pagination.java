package e3i2.ecommerce_backoffice.domain.admin.dto.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagination {

    private int page;        // 1-based
    private int limit;       // size
    private long total;      // total elements
    private int totalPages;  // total pages

    public static Pagination regist(int page, int limit, long total, int totalPages) {
        Pagination pagination = new Pagination();
        pagination.page = page;
        pagination.limit = limit;
        pagination.total = total;
        pagination.totalPages = totalPages;
        return pagination;
    }
}
