package com.ecommerce.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTOForm {

    @NotBlank
    private String name;

    @Positive
    private BigDecimal price;

    private String description;

    @NotBlank
    private String image;

    private boolean highlight;

    private boolean promotion;

    @Positive
    private BigDecimal promotionPrice;

    @NotNull
    private Long categoryId;

}
