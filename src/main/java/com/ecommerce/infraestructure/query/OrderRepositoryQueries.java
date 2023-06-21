package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.models.Order;

import java.util.List;

public interface OrderRepositoryQueries {

    List<Order> listAll(Long userId);

}
