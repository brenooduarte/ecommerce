package com.ecommerce.domain.dto.view;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTOView {

    private String name;

    private BigDecimal price;

    private String description;

    private String image;

    private boolean highlight;

    private boolean promotion;

    @JsonProperty("price_promotion")
    private BigDecimal pricePromotion;
	
}
