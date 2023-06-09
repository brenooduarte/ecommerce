package com.ecommerce.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "tb_product_order")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_id"))
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_id"))
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
