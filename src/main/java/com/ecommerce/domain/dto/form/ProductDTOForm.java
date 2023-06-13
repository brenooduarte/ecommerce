package com.ecommerce.domain.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTOForm {

    private String name;

    private String description;

    private String image;

    private boolean highlight;

    private boolean promotion;

    private String price;

    @JsonProperty("price_promotion")
    private String pricePromotion;

    @JsonProperty("category_id")
    private Long categoryId;

}
