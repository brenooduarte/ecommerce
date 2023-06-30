package com.ecommerce.infraestructure.query;

import com.ecommerce.domain.dto.form.ProductDTOFormWithId;

import java.util.List;

public interface ProductOrderRepositoryQueries {

    void insertInProductOrder(Long orderId, List<ProductDTOFormWithId> products);

}
