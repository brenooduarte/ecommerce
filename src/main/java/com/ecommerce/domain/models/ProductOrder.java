package com.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_product_order")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

}
