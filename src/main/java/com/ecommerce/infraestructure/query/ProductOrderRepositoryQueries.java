package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.dto.form.ProductDTOFormWithId;

import java.util.Set;

public interface ProductOrderRepositoryQueries {

    void insertInProductOrder(Long orderId, Set<ProductDTOFormWithId> products);

}
