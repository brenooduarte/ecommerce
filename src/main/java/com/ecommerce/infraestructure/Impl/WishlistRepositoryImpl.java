package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.models.Wishlist;
import com.ecommerce.infraestructure.query.WishlistRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepositoryImpl implements WishlistRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> listAllProductsInWishlist(Long userId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Wishlist> wishlistRoot = criteriaQuery.from(Wishlist.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        criteriaQuery.select(productRoot);
        criteriaQuery.where(criteriaBuilder.equal(wishlistRoot.get("user").get("id"), userId));

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();

    }
}
