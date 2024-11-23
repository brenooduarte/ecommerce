package com.ecommerce.domain.dto.view;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTOView {

    private Long id;

    private String name;

    private String brand;

    private BigDecimal price;

    private String description;

    private String image;

    private boolean highlight;

    private boolean promotion;

    private String related;

    @JsonProperty("price_promotion")
    private BigDecimal pricePromotion;
	
}
