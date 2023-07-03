package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.dto.view.ProductDTOView;
import com.ecommerce.domain.models.Product;
import com.ecommerce.domain.models.Wishlist;
import com.ecommerce.infraestructure.query.WishlistRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepositoryImpl implements WishlistRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<ProductDTOView> listAllProductsInWishlist(Long userId, PageRequest pageRequest) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Wishlist> wishlistRoot = criteriaQuery.from(Wishlist.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        criteriaQuery.select(productRoot);
        criteriaQuery.where(
                criteriaBuilder.equal(wishlistRoot.get("user").get("id"), userId),
                criteriaBuilder.equal(wishlistRoot.get("product").get("id"), productRoot.get("id")),
                criteriaBuilder.equal(productRoot.get("status"), GlobalConstants.ACTIVE));
        productRoot.fetch("assessments", JoinType.LEFT);
        productRoot.fetch("category", JoinType.LEFT);

        int offset = pageRequest.getPageNumber() * pageRequest.getPageSize();
        int limit = pageRequest.getPageSize();

        List<ProductDTOView> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList()
                .stream().map(ProductDTOView::new).toList();

        return new PageImpl<>(query);

    }

    @Override
    public void removeProduct(Long productId, Long userId) {
        String sql = "DELETE FROM tb_wishlist tw WHERE tw.product_id = ? AND tw.user_id = ?";
        jdbcTemplate.update(sql, productId, userId);
    }

    @Override
    public void addProductInWishlist(Long productId, Long userId) {
        String sql = "INSERT INTO tb_wishlist (product_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, productId, userId);
    }
}
