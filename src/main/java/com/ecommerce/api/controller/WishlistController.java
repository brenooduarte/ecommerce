package com.ecommerce.api.controller;

import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.repository.WishlistRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@Tag(name = "Wishlist", description = "Controller de Wishlist")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @GetMapping("/products/user/{userId}")
    public ResponseEntity<Page<ProductDTOView>> listAllProductsInWishlist(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ProductDTOView> list = wishlistRepository.listAllProductsInWishlist(userId, pageRequest);
            return ResponseEntity.ok(list);

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping("products/{productId}/user/{userId}")
    public ResponseEntity<?> removeProduct(
            @PathVariable Long productId,
            @PathVariable Long userId
    ) {
        wishlistRepository.removeProduct(productId, userId);
        return ResponseEntity.noContent().build();
    }

}
