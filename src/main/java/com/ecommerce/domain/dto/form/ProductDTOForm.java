package com.ecommerce.domain.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTOForm {

    private String name;

    private String brand;

    private String description;

    @JsonProperty("image_url")
    private String imageUrl;

    private boolean highlight;

    private boolean promotion;

    private BigDecimal price;

    private String related;

    @JsonProperty("price_promotion")
    private BigDecimal pricePromotion;

    @JsonProperty("category_id")
    private Long categoryId;

    @NotNull
    @JsonProperty("store_id")
    private Long storeId;

}
