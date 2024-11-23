package com.ecommerce.domain.dto.view;

import lombok.Data;

import java.util.Set;

@Data
public class SearchedProductsDTOView {

    private Set<ProductDTOView> productDTOViews;

    private Long quantityProducts;
	
}
