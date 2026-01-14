package e3i2.ecommerce_backoffice.domain.product.controller;

import e3i2.ecommerce_backoffice.domain.product.dto.CreateProductRequest;
import e3i2.ecommerce_backoffice.domain.product.dto.CreateProductResponse;
import e3i2.ecommerce_backoffice.domain.product.dto.SearchProductResponse;
import e3i2.ecommerce_backoffice.domain.product.dto.common.ProductApiResponse;
import e3i2.ecommerce_backoffice.domain.product.dto.common.ProductsWithPagination;
import e3i2.ecommerce_backoffice.domain.product.entity.ProductCategory;
import e3i2.ecommerce_backoffice.domain.product.entity.ProductStatus;
import e3i2.ecommerce_backoffice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    // TODO 세션 관련 후처리 예정
    @PostMapping("/products")
    public ResponseEntity<ProductApiResponse<CreateProductResponse>> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductApiResponse.created(productService.createProduct(request)));
    }

    @GetMapping("/products")
    public ResponseEntity<ProductApiResponse<ProductsWithPagination>> getProduct(
            @RequestParam String productName
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer limit
            , @RequestParam(defaultValue = "createdAt") String sortBy
            , @RequestParam(defaultValue = "desc") String sortOrder
            , @RequestParam(required = false) ProductCategory category
            , @RequestParam(required = false) ProductStatus status
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(ProductApiResponse.search(productService.searchProduct(productName, category, status, page, limit, sortBy, sortOrder)));
    }
}
