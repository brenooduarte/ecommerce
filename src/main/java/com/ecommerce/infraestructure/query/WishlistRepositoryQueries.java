package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Product;

import java.util.List;

public interface WishlistRepositoryQueries {

    List<Product> listAllProductsInWishlist(Long userId);

}
