package e3i2.ecommerce_backoffice.domain.product.repository.projection;

public interface ReviewSummaryProjection {
    Double averageRating = 0.0;
    Integer totalReviews = 0;
    Integer fiveStarCount = 0;
    Integer fourStarCount = 0;
    Integer threeStarCount = 0;
    Integer twoStarCount = 0;
    Integer oneStarCount = 0;
 }
