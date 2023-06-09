package com.ecommerce.domain.models;

import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

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

    @Column(name = "promotion_price")
    private BigDecimal promotionPrice;

    @Column
    private boolean status;

    @PrePersist
    public void prePersist() {
    	setStatus(GlobalConstants.ACTIVE);
    }

}