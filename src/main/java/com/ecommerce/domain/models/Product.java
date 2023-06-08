package com.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.utils.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column
    private String image;

    @Column
    private boolean highlight;

    @Column(nullable = false)
    private boolean promotion;

    @Column(name = "price_promotion")
    private BigDecimal pricePromotion;

    @Column
    private boolean status;

    @PrePersist
    public void prePersist() {
    	setStatus(GlobalConstants.ACTIVE);
    }

}