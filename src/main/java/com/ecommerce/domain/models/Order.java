package com.ecommerce.domain.models;

import com.ecommerce.domain.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(name = "delivery_address")
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "user_customer_id", nullable = false)
    private User customer;

    @Enumerated
    @Column(name = "status_order", nullable = false)
    private StatusOrder statusOrder;

    @OneToMany
    private List<ProductOrder> items = new ArrayList<>();

    public Order(Date creationDate, StatusOrder status) {
        this.creationDate = creationDate;
        this.statusOrder = status;
    }

}
