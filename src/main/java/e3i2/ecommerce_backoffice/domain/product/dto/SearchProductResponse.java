package e3i2.ecommerce_backoffice.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import e3i2.ecommerce_backoffice.domain.review.dto.SearchReviewResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({
        "id"
        , "productName"
        , "category"
        , "price"
        , "quantity"
        , "status"
        , "createdAt"
        , "adminId"
        , "adminName"
        , "adminEmail"
})
public class SearchProductResponse {
    private Long id;
    private String productName;
    private String category;
    private Long price;
    private Long quantity;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private Long adminId;
    private String adminName;
    private String adminEmail;

    public static SearchProductResponse register(
            Long id, String productName, String category, Long price, Long quantity, String status, LocalDateTime createdAt
            , Long adminId, String adminName, String adminEmail
    ) {
        SearchProductResponse response = new SearchProductResponse();
        response.id = id;
        response.productName = productName;
        response.category = category;
        response.price = price;
        response.quantity = quantity;
        response.status = status;
        response.createdAt = createdAt;
        response.adminId = adminId;
        response.adminName = adminName;
        response.adminEmail = adminEmail;

        return response;
    }
}
