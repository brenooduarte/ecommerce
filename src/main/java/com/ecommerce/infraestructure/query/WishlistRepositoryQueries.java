package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.dto.view.ProductDTOView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface WishlistRepositoryQueries {

    Page<ProductDTOView> listAllProductsInWishlist(Long userId, PageRequest pageRequest);
    void removeProduct(Long productId, Long userId);
    void addProductInWishlist(Long productId, Long userId);

}
