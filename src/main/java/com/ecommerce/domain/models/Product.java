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

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private boolean highlight;

    @Column
    private boolean promotion;

    @Column(name = "promotion_price")
    private BigDecimal promotionPrice;

    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @PrePersist
    public void prePersist() {
    	setStatus(GlobalConstants.ACTIVE);
    }

}