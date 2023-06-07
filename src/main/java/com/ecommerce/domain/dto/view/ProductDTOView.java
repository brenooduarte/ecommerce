package com.ecommerce.domain.dto.view;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTOView {

    private String name;

    private BigDecimal price;

    private String description;

    private String image;

    private boolean highlight;

    private boolean promotion;

    private BigDecimal pricePromotion;
	
}
