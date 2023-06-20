package com.ecommerce.domain.dto.view;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.domain.models.Assessment;
import com.ecommerce.domain.models.Product;
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

    private List<Assessment> assessments;

    public ProductDTOView(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.highlight = product.isHighlight();
        this.promotion = product.isPromotion();
        this.pricePromotion = product.getPromotionPrice();
        this.assessments = product.getAssessments();
    }

}
