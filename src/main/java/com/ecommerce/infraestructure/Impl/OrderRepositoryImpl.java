package com.ecommerce.infraestructure.Impl;

import com.ecommerce.domain.models.Order;
import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.OrderRepositoryQueries;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryQueries {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Order> listAll(Long userId) {

        /**
         select tor
         from tb_user tu, tb_product_order tpo, tb_order tor
         where tu.id = tor.user_customer_id
         and tpo.order_id = tor.id;
         */

        StringBuilder consulta = new StringBuilder();

        consulta.append("SELECT tor ")
                .append("FROM tb_product_order tpo, tb_order tor ")
                .append("where :userId = tor.user_customer_id ")
                .append("and tpo.order_id = tor.id");

        Query query = entityManager.createNativeQuery(consulta.toString());
        query.setParameter("userId", userId);

        return query.getResultList();
    }

}
