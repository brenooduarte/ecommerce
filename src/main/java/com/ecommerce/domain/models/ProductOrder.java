package com.ecommerce.domain.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tb_product_order")
@NoArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price_on_purchase")
    private BigDecimal priceOnPurchase;

    @Column(name = "promotion_price")
    private BigDecimal promotionPrice;

    public ProductOrder(Order order, Product product) {
        this.order = order;
        this.product = product;
        this.priceOnPurchase = product.getPrice();
        if (product.getPromotionPrice() != null) {
            this.promotionPrice = product.getPromotionPrice();
        }
    }
}
