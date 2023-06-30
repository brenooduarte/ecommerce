package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.dto.form.ProductDTOFormWithId;
import com.ecommerce.infraestructure.query.ProductOrderRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductOrderRepositoryImpl implements ProductOrderRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertInProductOrder(Long orderId, List<ProductDTOFormWithId> products) {

        int size = products.size();

        if (size > 0) {
            StringBuilder sqlBuilder = new StringBuilder();
            List<Object> args = new ArrayList<>();

            sqlBuilder.append("INSERT INTO tb_product_order (price_on_purchase, promotion_price, order_id, product_id) VALUES ");

            for (int i = 0; i < size; i++) {
                ProductDTOFormWithId productDTOForm = products.get(i);
                sqlBuilder.append("(?, ?, ?, ?)");

                args.add(productDTOForm.getPrice());
                args.add(productDTOForm.getPricePromotion());
                args.add(orderId);
                args.add(productDTOForm.getId());

                if (i < size - 1) {
                    sqlBuilder.append(", ");
                }
            }

            String sql = sqlBuilder.toString();
            jdbcTemplate.update(sql, args.toArray());
        }

    }
}
