package e3i2.ecommerce_backoffice.domain.product.service;

import e3i2.ecommerce_backoffice.domain.product.dto.CreateProductRequest;
import e3i2.ecommerce_backoffice.domain.product.dto.CreateProductResponse;
import e3i2.ecommerce_backoffice.domain.product.dto.SearchProductResponse;
import e3i2.ecommerce_backoffice.domain.product.dto.common.ProductsWithPagination;
import e3i2.ecommerce_backoffice.domain.product.entity.Product;
import e3i2.ecommerce_backoffice.domain.product.entity.ProductCategory;
import e3i2.ecommerce_backoffice.domain.product.entity.ProductStatus;
import e3i2.ecommerce_backoffice.domain.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public CreateProductResponse createProduct(@Valid CreateProductRequest request) {
        Product product = Product.register(
                request.getName()
                , request.getCategory()
                , request.getPrice()
                , request.getQuantity()
                , request.getStatus()
        );

        Product saveProduct = productRepository.save(product);

        return CreateProductResponse.register(
                saveProduct.getProductId()
                , saveProduct.getProductName()
                , saveProduct.getCategory().getCategoryCode()
                , saveProduct.getPrice()
                , saveProduct.getQuantity()
                , saveProduct.getStatus().getStatusCode()
                , saveProduct.getCreatedAt()
                //, saveProduct.getAdmin().getAdminId()
                //, saveProduct.getAdmin().getAdminName()
                //, saveProduct.getAdmin().getEmail()
        );
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductsWithPagination searchProduct(String productName, ProductCategory category, ProductStatus status, Integer page, Integer limit, String sortBy, String sortOrder) {
        List<SearchProductResponse> items = productRepository.findProducts(
                        productName
                        , category
                        , status,
                        PageRequest.of(page - 1, limit, Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy)))
                .stream()
                .map(product -> SearchProductResponse.register(
                        product.getProductId()
                        , product.getProductName()
                        , product.getCategory().getCategoryCode()
                        , product.getPrice()
                        , product.getQuantity()
                        , product.getStatus().getStatusCode()
                        , product.getCreatedAt()
                        //, saveProduct.getAdmin().getAdminId()
                        //, saveProduct.getAdmin().getAdminName()
                        //, saveProduct.getAdmin().getEmail()
                )).toList();

        return ProductsWithPagination.register(items, page, limit, (long) items.size());
    }
}
