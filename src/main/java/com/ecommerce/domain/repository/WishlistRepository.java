package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.Wishlist;
import com.ecommerce.infraestructure.query.WishlistRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>, WishlistRepositoryQueries {
}
