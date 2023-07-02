package com.ecommerce.domain.models;

import com.ecommerce.domain.dto.form.ProductDTOFormWithId;
import com.ecommerce.domain.enums.StatusOrder;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "tb_order")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "freight_charge", nullable = false)
    private BigDecimal freightCharge;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "confirmation_date")
    private Date confirmationDate;

    @Column(name = "cancellation_date")
    private Date cancellationDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "delivery_address", nullable = false)
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonManagedReference
    private User customer;

    @ManyToMany
    @JoinTable(name = "tb_product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_order", nullable = false)
    private StatusOrder statusOrder;

    public Order(User user, BigDecimal subtotal, BigDecimal freightCharge, BigDecimal totalAmount) {
        this.subtotal = subtotal;
        this.freightCharge = freightCharge;
        this.totalAmount = totalAmount;
        this.creationDate = new Date();
        this.statusOrder = StatusOrder.CREATED;
        this.customer = user;
    }

    public void setProducts(Set<ProductDTOFormWithId> productDTOFormWithIds) {
        this.products = parseToProduct(productDTOFormWithIds);
    }

    private Set<Product> parseToProduct(Set<ProductDTOFormWithId> productDTOFormWithIds) {
        return productDTOFormWithIds.stream().map(Product::new).collect(Collectors.toSet());
    }
}
