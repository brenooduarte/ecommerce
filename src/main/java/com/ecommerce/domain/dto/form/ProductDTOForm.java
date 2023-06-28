package com.ecommerce.domain.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTOForm {

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private String image;

    private boolean highlight;

    private boolean promotion;

    @JsonProperty("price_promotion")
    private BigDecimal pricePromotion;

    @JsonProperty("category_id")
    private Long categoryId;

}
