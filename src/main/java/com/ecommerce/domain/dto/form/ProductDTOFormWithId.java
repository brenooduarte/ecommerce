package com.ecommerce.domain.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTOFormWithId {

    @NotBlank
    private Long id;

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

    @NotBlank
    private Long categoryId;

}
